import {Component, OnInit} from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {APIInformation} from "./Infomation/apiinformation";
import {InformationService} from "./Infomation/information.service";
import {WeatherInformation} from "./Infomation/weather-information";
import {EventInformation} from "./Infomation/event-information";
import {NewsInformation} from "./Infomation/news-information";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{

  title: string;
  info : APIInformation[][][] |undefined;

  constructor(private informationService: InformationService) {
    this.title = 'Spring Boot - Angular Application';
  }

  ngOnInit(): void {
    this.informationService.findAll().subscribe(data => {
      this.info = data;
      //alert(this.info[0][0][0].locationName)
      let b = this.info[0][0][0] as WeatherInformation
      alert(b.apiName+": "+b.locationName + ": "+b.temperature)

      for (let i=0; i<2;i++){
        let c = this.info[0][1][i] as EventInformation
        alert(c.apiName+": "+c.locationName+ ":" + c.eventName);
      }

      for (let j=0; j<2;j++){
        let c = this.info[0][2][j] as NewsInformation
        alert(c.apiName+": "+c.locationName+ ":" + c.title);
      }

    });
  }

}
