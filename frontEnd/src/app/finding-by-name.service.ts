import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable()
export class FindingByNameService {

  private url: string;
  constructor(private http: HttpClient) {
    this.url  = "http://localhost:8080/addLocation";
  }

  public save(name: string | undefined){
    return this.http.post<string>(this.url,name);
  }

  public giveConfirmation(){
    return this.http.get<boolean>(this.url);
  }
}
