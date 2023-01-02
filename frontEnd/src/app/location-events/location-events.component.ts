import { Component, OnInit } from '@angular/core';
import {APIInformation} from "../Infomation/apiinformation";
import {WeatherInformation} from "../Infomation/weather-information";
import {NewsInformation} from "../Infomation/news-information";
import {EventInformation} from "../Infomation/event-information";
import {InformationService} from "../Infomation/information.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-location-events',
  templateUrl: './location-events.component.html',
  styleUrls: ['./location-events.component.css']
})
export class LocationEventsComponent implements OnInit {
  title: string;
  completeInfo !:APIInformation[][][];
  info !: APIInformation[][][];
  weatherInfo! : WeatherInformation
  newsInfo! : NewsInformation
  eventInfo! : EventInformation
  mylocations !: string[]
  addCounter : number;

  constructor(private informationService: InformationService,
              private route: ActivatedRoute,
              private router: Router) {
    this.title = 'Spring Boot - Angular Application';
    this.informationService.setAppComponent(this);
    this.addCounter = 1;
  }

  ngOnInit(): void {
    if (this.informationService.filterSelected == undefined) {
      this.addCounter = 1;
      if (sessionStorage.getItem("email") != null) {
        this.informationService.findAll().subscribe(data => {
          this.info = data;
          this.completeInfo = data;
          this.informationService.giveActiveLocationsNameList().subscribe(data2 => {
            this.mylocations = data2;
          });
        });
      }
      else {
        this.router.navigate(['/login']);
      }
    }
  }

  changesOnFilter(){
    if (this.informationService.filterSelected == undefined) {
      this.info = this.completeInfo

    } else {
      this.addCounter = this.informationService.filterSelected+1;
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

  goApiList(){
    this.router.navigate(['/apisList']);
  }
}
