import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Location} from "./location";
import {Api} from "./api";

@Injectable()
export class FindingByNameService {

  constructor(private http: HttpClient) {
  }

  public save(name: string | undefined){

    //alert("a" +sessionStorage.getItem("email"));
    return this.http.post("http://localhost:8080/addLocation",sessionStorage.getItem("email")+"#"+name);
  }

  public giveConfirmation(){
    // @ts-ignore
    const params = new HttpParams().set("email",sessionStorage.getItem("email"));

    return this.http.get<boolean>("http://localhost:8080/addLocation", {params});
  }


  public giveCityList(){
    return this.http.get<string[]>("http://localhost:8080/giveAutocompleteLocations");
  }

  public addLocation(location: string | undefined) {
    return this.http.post<string>("http://localhost:8080/addLocation",sessionStorage.getItem("email")+"#"+location);
  }

  public getLocationList(){
    // @ts-ignore
    const params = new HttpParams().set("email",sessionStorage.getItem("email"));
    return this.http.get<Location[]>("http://localhost:8080/giveLocations", {params});
  }

  public changeActiveState(location: Location | undefined){
    return this.http.post<string>("http://localhost:8080/changeActiveState",sessionStorage.getItem("email")+"#"+location?.name);
  }

  public changeAlias(name: string, alias: string) {
    return this.http.post<string>("http://localhost:8080/changeAlias",sessionStorage.getItem("email")+"#"+name+'#'+alias);
  }

  public deleteLocation(name: string | undefined) {
    return this.http.post<string>("http://localhost:8080/deleteLocation",sessionStorage.getItem("email")+"#"+name);
  }

  public giveAvailableApis(){
    // @ts-ignore
    const params = new HttpParams().set("email",sessionStorage.getItem("email"));
    return this.http.get<Api[]>("http://localhost:8080/giveAvailableApis", {params});
  }

  public changeAPIState(order : number | undefined){
    return this.http.post<string>("http://localhost:8080/changeAPIState",sessionStorage.getItem("email")+"#"+order);
  }

  public changeActiveStateLocationAPI(location: Location,order : number | undefined){
    return this.http.post<string>("http://localhost:8080/changeLocationApiState",sessionStorage.getItem("email")+"#"+location.name+"#"+order);
  }

  public giveAPIConfirmation(){
    return this.http.get<boolean>("http://localhost:8080/changeActiveApiState");
  }
}
