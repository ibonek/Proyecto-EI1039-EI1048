import { Component, OnInit } from '@angular/core';
import {Location} from "../location";
import {ActivatedRoute, Router} from "@angular/router";
import {FindingByNameService} from "../finding-by-name.service";
import {Api} from "../api";



@Component({
  selector: 'app-api-list',
  templateUrl: './api-list.component.html',
  styleUrls: ['./api-list.component.css']
})
export class ApiListComponent implements OnInit {

  apis!: Api[]

  constructor(private route: ActivatedRoute,
              private router: Router,
              private findingByNameService: FindingByNameService) { }

  ngOnInit(): void {
    this.findingByNameService.giveAvailableApis().subscribe(data => {
      this.apis=data;}
    );
  }

  changeActiveState(api: Api | undefined) {
    this.findingByNameService.changeActiveStateApi(api).subscribe(data=> {});
  }
}
