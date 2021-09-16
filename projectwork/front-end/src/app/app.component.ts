import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {AuthenticationService} from "./services/authentication.service";
import {User} from "./dto/user";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  currentUser?: User;
  title = 'front-end';

  constructor(private router: Router,
              private authenticationService: AuthenticationService) {
    this.authenticationService.currentUser.subscribe(p => this.currentUser = p);
  }

  ngOnInit(): void {
  }

  logOut() {
    this.authenticationService.logout();
    this.router.navigate(['/login']);
  }
}
