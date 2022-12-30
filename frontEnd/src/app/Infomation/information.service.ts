import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {APIInformation} from "./apiinformation";
import {AppComponent} from "../app.component";
import {LocationEventsComponent} from "../location-events/location-events.component";

@Injectable({
  providedIn: 'root'
})
export class InformationService {

  private infoUrl: string;
  filterSelected: number |undefined;

  component!: LocationEventsComponent

  constructor(private http: HttpClient) {
    this.infoUrl ='http://localhost:8080/apiInfo';
  }

  public findAll(): Observable<APIInformation[][][]>{
    // @ts-ignore
    const params = new HttpParams().set("email",sessionStorage.getItem("email"));

    return this.http.get<APIInformation[][][]>(this.infoUrl,{params});

  }
  public giveActiveLocationsNameList(){
    // @ts-ignore
    const params = new HttpParams().set("email",sessionStorage.getItem("email"));

    return this.http.get<string[]>("http://localhost:8080/activeLocationsNames",{params});
  }

  public setAppComponent(appComponent: LocationEventsComponent){
    this.component = appComponent;
  }
  public getFilter(){
    this.component.changesOnFilter();
  }

}
