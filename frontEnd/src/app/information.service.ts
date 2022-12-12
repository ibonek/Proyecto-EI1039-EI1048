import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {APIInformation} from "./apiinformation";

@Injectable({
  providedIn: 'root'
})
export class InformationService {

  private infoUrl: string
  constructor(private http: HttpClient) {
    this.infoUrl ='http://localhost:8080/apiInfo';
  }

  public findAll(): Observable<APIInformation[]>{

    let a = this.http.get<APIInformation[]>(this.infoUrl);
    return a;
  }
}
