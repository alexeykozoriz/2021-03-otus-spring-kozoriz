import {Component, OnInit} from '@angular/core';
import {HalService} from "../services/hal.service";
import {environment} from "../../environments/environment";
import {FormControl} from "@angular/forms";
import {map} from "rxjs/operators";
import {Observable} from "rxjs";

@Component({
  selector: 'app-dialog',
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.css']
})
export class DialogComponent implements OnInit {

  authorControl = new FormControl();
  authors: string[] = [];
  filteredAuthors: Observable<string[]> = new Observable<string[]>();
  author?: string;

  genreControl = new FormControl();
  genres: string[] = [];
  filteredGenres: Observable<string[]> = new Observable<string[]>();
  genre?: string;

  titleControl = new FormControl();
  title?: string;

  publicationYearControl = new FormControl();
  publicationYear?: number;

  submit: boolean = false;

  constructor(private halService: HalService) {
  }

  ngOnInit(): void {
    this.getAuthors();
    this.getGenres();
    this.submit = false;
  }

  getAuthors(): void {
    this.halService.getEmbeddedList(`${environment.apiUrl}/authors`).subscribe(p => this.authors = p.map(q => q.getSummary()));
    this.filteredAuthors = this.authorControl.valueChanges.pipe(map(value => {
      const filterValue = value.toLowerCase();
      return this.authors.filter(option => option.toLowerCase().includes(filterValue));
    }));
  }

  getGenres(): void {
    this.halService.getEmbeddedList(`${environment.apiUrl}/genres`).subscribe(p => this.genres = p.map(q => q.getSummary()));
    this.filteredGenres = this.genreControl.valueChanges.pipe(map(value => {
      const filterValue = value.toLowerCase();
      return this.genres.filter(option => option.toLowerCase().includes(filterValue));
    }));
  }

  onSubmit() {
    this.submit = true;
  }
}
