import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable()
export class UserService {

  constructor(private http: HttpClient) { }

  public register(email: string){
    return this.http.post<string>("http://localhost:8080/registerUser",email);
  }

  public getConfirmation(){
    return this.http.get<Boolean>("http://localhost:8080/registerUser");
  }

  public signOut(email: string | null){
    return this.http.post<string>("http://localhost:8080/signOut",email)
  }

  public signIn(email: string){
    return this.http.post<string>("http://localhost:8080/signIn",email);
  }
}
