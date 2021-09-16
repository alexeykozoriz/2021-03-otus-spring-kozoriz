import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from "rxjs";
import {User} from "../dto/user";
import {environment} from "../../environments/environment";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {map} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  public currentUser: Observable<User>;
  private currentUserSubject: BehaviorSubject<User>;

  constructor(private httpClient: HttpClient) {
    this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('currentUser') ?? "{}"));
    this.currentUser = this.currentUserSubject.asObservable();
  }

  public get currentUserValue(): User {
    return this.currentUserSubject.value;
  }

  logout() {
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(new User());
  }

  login(username: string, password: string) {
    return this.httpClient.get<any>(environment.apiUrl, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Basic ${window.btoa(username + ':' + password)}`
      }),
      observe: 'response'
    })
      .pipe(map(response => {
        let user = new User()
        user.auth = window.btoa(username + ':' + password);
        user.username = username;
        user.authorities = response.headers.get("X-Authorities") ?? "";
        localStorage.setItem('currentUser', JSON.stringify(user));
        this.currentUserSubject.next(user);
      }));
  }
}
