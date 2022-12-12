import { Component } from '@angular/core';

@Component({
  selector: 'app-createform',
  templateUrl: './createform.component.html',
  styleUrls: ['./createform.component.css']
})
export class CreateformComponent {
  book = {
    category: "",
    code: "",
    title: "",
    author: "",
    price: "",
    publisher: "",
    publisheddate:"",
    logopath:"",
    cotent:""
  }
  public createbook() {
    // const observable = this.userService.search(this.search);
    // observable.subscribe((response: any) => {
    //  console.log(response);
    // })
  }
}
