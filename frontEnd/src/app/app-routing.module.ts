import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterModule, Routes} from "@angular/router";
import {CoordenadasListComponent} from "./coordenadas-list/coordenadas-list.component";
import {CoordenadasFormComponent} from "./coordenadas-form/coordenadas-form.component";


const routes: Routes= [
  {path: 'coordenadas', component: CoordenadasListComponent},
  {path: 'addCoordenadas', component: CoordenadasFormComponent}];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
