import {Component, OnInit} from '@angular/core';
import {HalService} from "../services/hal.service";
import {environment} from "../../environments/environment";
import {map, mergeMap} from "rxjs/operators";
import {forkJoin, Observable, of} from "rxjs";
import {PageEvent} from "@angular/material/paginator";
import {HateoasObject} from "../dto/hateoas-object";
import {DialogComponent} from "../dialog/dialog.component";
import {AuthenticationService} from "../services/authentication.service";
import {MatDialog} from "@angular/material/dialog";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  books: HateoasObject[] = [];
  page = {
    size: 0,
    totalElements: 0,
    totalPages: 0,
    number: 1
  };

  constructor(private authenticationService: AuthenticationService,
              private halService: HalService,
              private matDialog: MatDialog) {
  }

  ngOnInit(): void {
    this.loadBooks();
  }

  /**
   * Загрузка всех книг
   */
  loadBooks(pageEvent?: PageEvent): void {
    let paging = "";
    if (pageEvent) {
      paging = "?page=" + pageEvent.pageIndex + "&size=" + pageEvent.pageSize;
    }
    this.halService.getEmbedded(`${environment.apiUrl}/books${paging}`).subscribe(
      (p) => {
        let result: HateoasObject[] = [];
        for (let embedded of Object.values(p._embedded)) {
          for (let embeddedItem of embedded) {
            result.push(HateoasObject.create(embeddedItem));
          }
        }
        this.books = result;
        let page = HateoasObject.create(p.page);
        this.page.size = Number(page.properties.get("size"));
        this.page.totalElements = Number(page.properties.get("totalElements"));
        this.page.totalPages = Number(page.properties.get("totalPages"));
        this.page.number = Number(page.properties.get("number"));
      });

  }

  /**
   * Удаление книги
   *
   * @param book книга
   */
  delete(book: HateoasObject) {
    if (!confirm("Are you sure?")) {
      return;
    }
    let url = book.links.get("self")?.href;
    if (!url) {
      return;
    }
    this.halService.delete(url).subscribe(() => this.loadBooks());
  }

  /**
   * Создание книги
   */
  create() {
    let matDialogRef = this.matDialog.open(DialogComponent, {
      disableClose: true,
      id: "create-dialog",
      width: "600px"
    });
    this.matDialog._getAfterAllClosed().subscribe(() => {
      if (!matDialogRef.componentInstance.submit) {
        return;
      }
      let author = matDialogRef.componentInstance.author ?? "";
      let genre = matDialogRef.componentInstance.genre ?? "";
      let bookTitle = matDialogRef.componentInstance.title ?? "";
      let bookPublicationYear = matDialogRef.componentInstance.publicationYear ?? 1970;
      forkJoin([this.buildAuthorUrl(author), this.buildGenreUrl(genre)])
        .subscribe(urls => this.createBook(bookTitle, bookPublicationYear, urls[0], urls[1])
          .subscribe(() => this.loadBooks()));
    });
  }

  /**
   * Обновление книги
   *
   * @param book книга
   */
  update(book: HateoasObject) {
    let matDialogRef = this.matDialog.open(DialogComponent, {
      disableClose: true,
      id: "update-dialog",
      width: "600px"
    });
    matDialogRef.componentInstance.title = book.properties.get("title")?.toString();
    matDialogRef.componentInstance.publicationYear = Number(book.properties.get("publicationYear"));
    matDialogRef.componentInstance.author = book.linkedObjects.get("author")?.getSummary();
    matDialogRef.componentInstance.genre = book.linkedObjects.get("genre")?.getSummary();
    this.matDialog._getAfterAllClosed().subscribe(() => {
      if (!matDialogRef.componentInstance.submit) {
        return;
      }
      let bookUrl = book.links.get("self")?.href ?? "";
      let author = matDialogRef.componentInstance.author ?? "";
      let genre = matDialogRef.componentInstance.genre ?? "";
      let bookTitle = matDialogRef.componentInstance.title ?? "";
      let bookPublicationYear = matDialogRef.componentInstance.publicationYear ?? 1970;
      forkJoin([this.buildAuthorUrl(author), this.buildGenreUrl(genre)])
        .subscribe(urls => this.updateBookLinks(bookUrl, urls[0], urls[1])
          .subscribe(() => this.halService.put(bookUrl, {
            title: bookTitle,
            publicationYear: bookPublicationYear
          })
            .subscribe(() => this.loadBooks())));
    });
  }

  /**
   * Получение связанных объектов
   *
   * @param book книга
   */
  getLinkedObjects(book: HateoasObject) {
    this.halService.getLinkedObjects(book);
  }

  isAdmin(): boolean {
    return this.authenticationService.currentUserValue.isAdmin();
  }

  /**
   * Получение или создание автора
   *
   * @param author имя автора
   * @private ссылка на автора
   */
  private buildAuthorUrl(author: string): Observable<string> {
    let authorsApiUrl = `${environment.apiUrl}/authors`;
    return this.halService.search(authorsApiUrl, "findByFullNameEquals", "fullName=" + author)
      .pipe(mergeMap((authorExisting) => {
        if (authorExisting == null) {
          return this.halService.post(authorsApiUrl, {fullName: author})
            .pipe(map((authorCreated) => {
              return HateoasObject.create(authorCreated).links.get("self")?.href ?? "";
            }));
        } else {
          let self = authorExisting.links.get("self")?.href ?? "";
          return of(self);
        }
      }));
  }

  /**
   * Получение или создание жанра
   *
   * @param genre название жанра
   * @private ссылка на жанр
   */
  private buildGenreUrl(genre: string): Observable<string> {
    let genresApiUrl = `${environment.apiUrl}/genres`;
    return this.halService.search(genresApiUrl, "findByTitleEquals", "title=" + genre)
      .pipe(mergeMap((genreExisting) => {
        if (genreExisting == null) {
          return this.halService.post(genresApiUrl, {title: genre})
            .pipe(map((genreCreated) => {
              return HateoasObject.create(genreCreated).links.get("self")?.href ?? "";
            }));
        } else {
          let self = genreExisting.links.get("self")?.href ?? "";
          return of(self);
        }
      }));
  }

  /**
   * Создание книги
   *
   * @param bookTitle название
   * @param bookPublicationYear год публикации
   * @param authorUrl ссылка на автора
   * @param genreUrl ссылка на жанр
   * @private подписка
   */
  private createBook(bookTitle: string, bookPublicationYear: number, authorUrl: string, genreUrl: string): Observable<void> {
    return this.halService.post(`${environment.apiUrl}/books`, {
      title: bookTitle,
      publicationYear: bookPublicationYear,
      author: authorUrl,
      genre: genreUrl
    });
  }

  /**
   * Обновление книги
   *
   * @param bookUrl ссылка на обновляемую книгу
   * @param authorUrl ссылка на автора
   * @param genreUrl ссылка на жанр
   * @private подписка
   */
  private updateBookLinks(bookUrl: string, authorUrl: string, genreUrl: string): Observable<any> {
    return forkJoin([
      this.halService.put(bookUrl + "/author", {
        _links: {
          author: {
            href: authorUrl
          }
        }
      }),
      this.halService.put(bookUrl + "/genre", {
        _links: {
          genre: {
            href: genreUrl
          }
        }
      })
    ]);
  }
}
