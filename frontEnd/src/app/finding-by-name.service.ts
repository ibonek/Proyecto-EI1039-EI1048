import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Location} from "./location";

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

  public giveCityList(){
    return this.http.get<string[]>("http://localhost:8080/giveAutocompleteLocations");
  }

  public getActiveLocationList(){
    return this.http.get<Location[]>("http://localhost:8080/giveLocations");
  }

  public changeActiveState(location: Location | undefined){
    return this.http.post<string>("http://localhost:8080/changeActiveState",location?.name);
  }

  public changeAlias(alias: string) {
    return this.http.post<string>("http://localhost:8080/changeAlias",alias);
  }
}
