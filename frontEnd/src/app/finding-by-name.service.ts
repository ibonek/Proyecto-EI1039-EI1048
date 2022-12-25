import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Location} from "./location";
import {Api} from "./api";

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

  public deleteLocation(name: string | undefined) {
    return this.http.post<string>("http://localhost:8080/deleteLocation",name);
  }

  public giveAvailableApis(){
    return this.http.get<Api[]>("http://localhost:8080/giveAvailableApis");
  }

  public changeActiveStateApi(order : number | undefined){
    return this.http.post<string>("http://localhost:8080/changeActiveApiState",order);
  }

  public giveAPIConfirmation(){
    return this.http.get<boolean>("http://localhost:8080/changeActiveApiState");
  }
}
