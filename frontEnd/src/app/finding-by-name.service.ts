import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Location} from "./location";

@Injectable()
export class FindingByNameService {

  constructor(private http: HttpClient) {
  }

  public save(name: string | undefined){
    return this.http.post<string>("http://localhost:8080/addLocation",name);
  }

  public giveConfirmation(){
    return this.http.get<boolean>("http://localhost:8080/addLocation");
  }

  public giveRepeatedConfirmation(){
    return this.http.get<boolean>("http://localhost:8080/repeatedLocation");

  }

  public giveCityList(){
    return this.http.get<string[]>("http://localhost:8080/giveAutocompleteLocations");
  }

  public getLocationList(){
    return this.http.get<Location[]>("http://localhost:8080/giveLocations");
  }

  public changeActiveState(location: Location | undefined){
    return this.http.post<string>("http://localhost:8080/changeActiveState",location?.name);
  }

  public changeAlias(name: string, alias: string) {
    return this.http.post<string>("http://localhost:8080/changeAlias",name+'#'+alias);
  }
}
