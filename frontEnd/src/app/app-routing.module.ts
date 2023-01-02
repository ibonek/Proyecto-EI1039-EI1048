import { NgModule } from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {LocationFormComponent} from "./location-form/location-form.component";
import {LocationListComponent} from "./location-list/location-list.component";
import {LocationEventsComponent} from "./location-events/location-events.component";
import {ApiListComponent} from "./api-list/api-list.component";
import {UserComponent} from "./userRegister/user.component";
import {LoginComponent} from "./login/login.component";
import {ResetPasswordComponent} from "./reset-password/reset-password.component";


const routes: Routes= [

  {path: 'addLocation', component: LocationFormComponent},
  {path: 'locationList', component: LocationListComponent},
  {path: 'events', component: LocationEventsComponent},
  {path: 'apisList', component: ApiListComponent},
  {path: '', component: UserComponent},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: UserComponent},
  {path: 'resetPassword', component: ResetPasswordComponent}
  ];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
