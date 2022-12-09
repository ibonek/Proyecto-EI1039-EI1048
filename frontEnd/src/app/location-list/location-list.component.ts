import {Component, OnInit} from '@angular/core';
import {map, startWith} from "rxjs/operators";
import {FindingByNameService} from "../finding-by-name.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-location-list',
  templateUrl: './location-list.component.html',
  styleUrls: ['./location-list.component.css']
})
export class LocationListComponent implements OnInit {

  locations: string[] | undefined

  constructor( private route: ActivatedRoute,
               private router: Router,
               private findingByNameService: FindingByNameService) { }

  async ngOnInit(){
    await this.findingByNameService.getActiveLocationList().subscribe(data => {
      this.locations=data;
    });
  }



}
