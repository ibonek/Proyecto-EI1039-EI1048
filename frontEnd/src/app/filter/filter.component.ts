import { Component, OnInit } from '@angular/core';
import {InformationService} from "../Infomation/information.service";
import {FormControl} from "@angular/forms";

@Component({
  selector: 'app-filter',
  templateUrl: './filter.component.html',
  styleUrls: ['./filter.component.css']
})
export class FilterComponent implements OnInit {
  filterList : string[];
  private selected: number | undefined;

  constructor(private informationService: InformationService) {
    this.filterList=[];
  }

  ngOnInit(): void {
    if (sessionStorage.getItem("email")!=null)
    this.informationService.giveActiveLocationsNameList().subscribe(data=>{
      this.filterList = data;
    });
  }

  filter(i: number){
    if (i==0){
      this.informationService.filterSelected=undefined
    } else {
      this.informationService.filterSelected = i -1;
    }
    this.informationService.getFilter();
  }


}
