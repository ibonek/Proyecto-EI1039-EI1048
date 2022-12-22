import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterModule, Routes} from "@angular/router";
import {LocationFormComponent} from "./location-form/location-form.component";
import {LocationListComponent} from "./location-list/location-list.component";
import {LocationEventsComponent} from "./location-events/location-events.component";


const routes: Routes= [

  {path: 'addLocation', component: LocationFormComponent},
  {path: 'locationList', component: LocationListComponent},
  {path: '', component: LocationEventsComponent}

  ];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
