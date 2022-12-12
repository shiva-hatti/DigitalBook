import { Component } from '@angular/core';
import { UserService } from '../user.service';
@Component({
  selector: 'app-searchform',
  templateUrl: './searchform.component.html',
  styleUrls: ['./searchform.component.css']
})

export class SearchformComponent {
  constructor(public userService: UserService) {

  }
  search = {
    category: "",
    title: "",
    author: "",
    price: "",
    publisher: ""
  }

  public searchbook() {
    const observable = this.userService.search(this.search);
    observable.subscribe((response: any) => {
     // sessionStorage.setItem('token', response.token);
     console.log(response);
    })
  }
}
