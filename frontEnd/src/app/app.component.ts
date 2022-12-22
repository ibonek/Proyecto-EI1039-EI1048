import {Component, Input, OnInit, ViewChild} from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {APIInformation} from "./Infomation/apiinformation";
import {InformationService} from "./Infomation/information.service";
import {WeatherInformation} from "./Infomation/weather-information";
import {EventInformation} from "./Infomation/event-information";
import {NewsInformation} from "./Infomation/news-information";
import {MatMenuTrigger} from "@angular/material/menu";
import {FilterComponent} from "./filter/filter.component";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{

  title: string;
  completeInfo !:APIInformation[][][];
  info !: APIInformation[][][];
  weatherInfo! : WeatherInformation
  newsInfo! : NewsInformation
  eventInfo! : EventInformation

  constructor(private informationService: InformationService) {
    this.title = 'Spring Boot - Angular Application';
    this.informationService.setAppComponent(this);
  }

  ngOnInit(): void {
    if (this.informationService.filterSelected == undefined) {

      this.informationService.findAll().subscribe(data => {

        this.info = data;
        this.completeInfo = data;
      });
    } else {
      this.informationService.findAll().subscribe(data => {
        // @ts-ignore
        this.info = [data[this.informationService.filterSelected]]
      });
    }
  }

  changesOnFilter(){
    if (this.informationService.filterSelected == undefined) {
      this.info = this.completeInfo

    } else {
        // @ts-ignore
        this.info = [this.completeInfo[this.informationService.filterSelected]];
      }
  }

  castWeatherInfo(information: APIInformation){
    this.weatherInfo =  information as WeatherInformation
  }

  castNewsInfo(information: APIInformation){
    this.newsInfo = information as NewsInformation
  }

  castEventsInfo(information: APIInformation){
    this.eventInfo = information as EventInformation
  }
}
