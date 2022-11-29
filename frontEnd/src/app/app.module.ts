import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import {MatGridListModule} from "@angular/material/grid-list";
import {MatListModule} from "@angular/material/list";
import { LocationFormComponent } from './location-form/location-form.component';
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatAutocompleteModule} from "@angular/material/autocomplete";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatOptionModule} from "@angular/material/core";
import {AppRoutingModule} from "./app-routing.module";
import {RouterOutlet, Routes} from "@angular/router";
import {HttpClient, HttpClientModule} from "@angular/common/http";
import {FindingByNameService} from "./finding-by-name.service";



@NgModule({
  declarations: [
    AppComponent,
    LocationFormComponent
  ],
  imports: [
    FormsModule,
    HttpClientModule,
    BrowserModule,
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    MatGridListModule,
    MatListModule,
    MatFormFieldModule,
    MatAutocompleteModule,
    ReactiveFormsModule,
    MatOptionModule,
    AppRoutingModule
  ],
  providers: [FindingByNameService],
  bootstrap: [AppComponent]
})
export class AppModule { }
