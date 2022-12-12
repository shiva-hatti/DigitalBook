import { Component } from '@angular/core';
import { UserService } from '../user.service';
@Component({
  selector: 'app-signinform',
  templateUrl: './signinform.component.html',
  styleUrls: ['./signinform.component.css']
})
export class SigninformComponent {
  constructor(public userService: UserService) {

  }
  user = {
    username: "",
    password: "",
  }
  public signin() {
    const observable = this.userService.signin(this.user);
    observable.subscribe((response: any) => {
      sessionStorage.setItem('token', response.token);
    })
  }
}
