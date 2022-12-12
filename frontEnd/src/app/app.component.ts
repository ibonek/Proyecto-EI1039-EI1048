import {Component, OnInit} from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {APIInformation} from "./apiinformation";
import {InformationService} from "./information.service";
import {WeatherInformation} from "./weather-information";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{

  title: string;
  info : APIInformation[] |undefined;

  constructor(private informationService: InformationService) {
    this.title = 'Spring Boot - Angular Application';
  }

  ngOnInit(): void {
    this.informationService.findAll().subscribe(data => {
      this.info = data;
      for (let i=0; i<this.info.length;i++){
        let b = this.info[i] as WeatherInformation
        alert(b.locationName)
      }

    });
  }

}
