import { Injectable } from '@angular/core';
import {LocationFormComponent} from "./location-form.component";
import {ActivatedRoute, Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class ConfirmationInputService {

  url: string;
  constructor( private locationForm: LocationFormComponent,
              private route: ActivatedRoute,
              private router: Router,
              private http: HttpClient) {
    this.url  = "http://localhost:8080/addLocation";
  }

  ngOnInit(){
    this.http.get<boolean>(this.url).subscribe(data =>{
      this.locationForm.setConfirmation(data);
      this.router.navigate(['/addLocation']);
    });

  }
}
