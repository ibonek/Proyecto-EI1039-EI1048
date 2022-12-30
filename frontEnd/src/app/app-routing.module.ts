import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterModule, Routes} from "@angular/router";
import {LocationFormComponent} from "./location-form/location-form.component";
import {LocationListComponent} from "./location-list/location-list.component";
import {LocationEventsComponent} from "./location-events/location-events.component";
import {ApiListComponent} from "./api-list/api-list.component";
import {UserComponent} from "./userRegister/user.component";


const routes: Routes= [

  {path: 'addLocation', component: LocationFormComponent},
  {path: 'locationList', component: LocationListComponent},
  {path: 'events', component: LocationEventsComponent},
  {path: 'apisList', component: ApiListComponent},
  {path: '', component: UserComponent}

  ];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
