import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

const URL = 'http://localhost:8080/api/auth/';
@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(public http: HttpClient) { }

  signup(user: any) {
    return this.http.post(URL+'signup', user);
  }

  signin(user: any) {
    return this.http.post(URL+'signin', user);
  }

  search(search: any) {
     return this.http.get(URL+'search?'+'category='+search.category+'&title='+search.title+'&author='+search.author+'&price='+search.price+'&publisher='+search.publisher);
  }
  
}