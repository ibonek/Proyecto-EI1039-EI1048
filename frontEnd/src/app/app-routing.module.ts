import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterModule, Routes} from "@angular/router";
import {LocationFormComponent} from "./location-form/location-form.component";
import {ConfirmationInputService} from "./location-form/confirmation-input.service";


const routes: Routes= [
  {path: 'addLocation', component: LocationFormComponent},
  {path: 'confirmationInput', component: ConfirmationInputService}

  ];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
