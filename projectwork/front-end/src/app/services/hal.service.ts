import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {Observable, of} from "rxjs";
import {HateoasEmbedded} from "../dto/hateoas-embedded";
import {HateoasObject} from "../dto/hateoas-object";
import {catchError, map} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class HalService {

  constructor(private httpClient: HttpClient) {
  }

  getEmbedded(url: string): Observable<HateoasEmbedded> {
    return this.httpClient.get<HateoasEmbedded>(url);
  }

  getEmbeddedList(url: string): Observable<HateoasObject[]> {
    return this.getEmbedded(url).pipe(
      map((p) => {
        let result: HateoasObject[] = [];
        for (let embedded of Object.values(p._embedded)) {
          for (let embeddedItem of embedded) {
            result.push(HateoasObject.create(embeddedItem));
          }
        }
        return result;
      }));
  }

  getLinkedObjects(hateoasObject: HateoasObject): void {
    for (let link of hateoasObject.links.entries()) {
      if (link[1].href == hateoasObject.links.get("self")?.href) {
        continue;
      }
      this.get(link[1].href).subscribe(p => {
        hateoasObject.linkedObjects.set(link[0], HateoasObject.create(p));
      });
    }
  }

  get(url: string): Observable<any> {
    return this.httpClient.get(url);
  }

  delete(url: string): Observable<any> {
    return this.httpClient.delete(url);
  }

  post(url: string, body: any): Observable<any> {
    return this.httpClient.post(url, body);
  }

  put(url: string, body: any): Observable<any> {
    return this.httpClient.put(url, body);
  }

  search(url: string, method: string, query: string): Observable<HateoasObject> {
    return this.httpClient.get(url + "/search/" + method + "?" + query).pipe(
      map((p) => HateoasObject.create(p)),
      catchError((error: HttpErrorResponse): Observable<any> => {
        return of(null);
      }));
  }
}
