import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable()
export class FindingByNameService {

  private url: string;
  constructor(private http: HttpClient) {
    this.url  = "http://localhost:8080/addLocation";
  }
  public findAll() : Observable<void>{
    return this.http.get<void>(this.url);
  }

  public save(name: string){
    return this.http.post<string>(this.url,name);
  }
}
