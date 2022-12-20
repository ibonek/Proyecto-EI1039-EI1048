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
  info !: APIInformation[][][];
  weatherInfo! : WeatherInformation
  newsInfo! : NewsInformation
  eventInfo! : EventInformation

  constructor(private informationService: InformationService) {
    this.title = 'Spring Boot - Angular Application';
  }

  ngOnInit(): void {


    this.informationService.findAll().subscribe(data => {
      this.info = data;
    });

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
