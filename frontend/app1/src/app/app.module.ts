import { Component, createComponent, NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { UserformComponent } from './userform/userform.component';
import { OrderformComponent } from './orderform/orderform.component';
import { HttpClientModule } from "@angular/common/http";
import { EllipsisPipe } from './ellipsis.pipe';
import { Route, RouterModule } from '@angular/router';
import { SearchformComponent } from './searchform/searchform.component';
import { SigninformComponent } from './signinform/signinform.component';
import { CreateformComponent } from './createform/createform.component';

const routes: Route[] = [
  { path: 'userform', component: UserformComponent },
  { path: 'orderform', component: OrderformComponent },
  { path: 'signinform', component: SigninformComponent },
  { path: 'searchform', component: SearchformComponent },
  { path: 'createform', component: CreateformComponent }
]
@NgModule({//decorator
  declarations: [
    AppComponent,
    UserformComponent,
    OrderformComponent,
    EllipsisPipe,
    SearchformComponent,
    SigninformComponent,
    CreateformComponent
  ],
  imports: [
    BrowserModule, FormsModule, HttpClientModule, RouterModule.forRoot(routes)
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
