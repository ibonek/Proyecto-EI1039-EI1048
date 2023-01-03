import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {FindingByNameService} from "../finding-by-name.service";
import {Api} from "../api";
import Swal from "sweetalert2";

@Component({
  selector: 'app-api-list',
  templateUrl: './api-list.component.html',
  styleUrls: ['./api-list.component.css']
})
export class ApiListComponent implements OnInit {

  apis!: Api[]
  apiInfo = ['A real time weather API that has access for current weather data for ' +
  'any location including over 200,000 cities in ranges of minute forecast for 1 hour, ' +
  'hourly forecast for 48 hours...',
    'The API provides access to content sourced from various platform, including Ticketmaster,' +
    ' Universe, FrontGate Tickets and Ticketmaster Resale (TMR).',
    'A real time news API that gets articles from 150.000 news publisher worldwide' +
    ' in more than 50 languages and information about the mentioned entities, topics and sentiment.']

  constructor(private route: ActivatedRoute,
              private router: Router,
              private findingByNameService: FindingByNameService) {
  }

  ngOnInit(): void {
    this.findingByNameService.giveAvailableApis().subscribe(data => {
      this.apis=data;
    }
    );
  }

  changeActiveState(order : number) {
    this.findingByNameService.changeAPIState(order).subscribe(data=> {
      this.findingByNameService.giveAPIConfirmation().subscribe(confirmation =>{
        if (confirmation){
          this.tinySuccessAlert(this.apis[order].isActive, this.apis[order].name)
        }else{
          this.tinyFailAlert();
        }
      })
    });
  }

  async tinySuccessAlert(active: boolean, name: string) {
    if (active){
      Swal.fire({
        title: 'Ok!',
        text: 'You will see information of '+name+' about your locations.',
        icon: 'success',
        showCancelButton: false,
        confirmButtonColor: '#c2185b',
        confirmButtonText: 'Ok'
      }).then(( result => {
        if (result.isConfirmed) {
          window.location.reload()
        }
      }))
    } else{
      Swal.fire({
        title: 'Ok!',
        text: 'You will no longer see information of '+name+' about your locations.',
        icon: 'success',
        showCancelButton: false,
        confirmButtonColor: '#c2185b',
        confirmButtonText: 'Ok'
      }).then(( result => {
        if (result.isConfirmed) {
          window.location.reload()
        }
      }))
    }


  }

  async tinyFailAlert(){
    await Swal.fire({
      title: 'Ooops',
      text: 'There is an error and you can\'t change that api state',
      icon: 'error',
      confirmButtonColor: '#c2185b',
      confirmButtonText: 'Ok'
    })

  }
}
