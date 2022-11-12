import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import {RouterOutlet, Routes} from "@angular/router";
import { CoordenadasListComponent } from './coordenadas-list/coordenadas-list.component';
import { CoordenadasFormComponent } from './coordenadas-form/coordenadas-form.component';
import {FormsModule} from "@angular/forms";
import {AppRoutingModule} from "./app-routing.module";
import {HttpClientModule} from "@angular/common/http";
import {CoordenadasService} from "./coordenadas.service";

const routes: Routes= [
  {path: 'coordenadas', component: CoordenadasListComponent},
  {path: 'addCoordenadas', component: CoordenadasFormComponent}];

@NgModule({
  declarations: [
    AppComponent,
    CoordenadasListComponent,
    CoordenadasFormComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [CoordenadasService],
  bootstrap: [AppComponent]
})
export class AppModule { }
