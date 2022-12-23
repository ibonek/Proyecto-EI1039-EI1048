import {Component, OnInit} from '@angular/core';
import {map, startWith} from "rxjs/operators";
import {FindingByNameService} from "../finding-by-name.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Location} from "../location";

@Component({
  selector: 'app-location-list',
  templateUrl: './location-list.component.html',
  styleUrls: ['./location-list.component.css']
})
export class LocationListComponent implements OnInit {

  locations!: Location[]

  constructor( private route: ActivatedRoute,
               private router: Router,
               private findingByNameService: FindingByNameService) { }

  ngOnInit(){
    this.findingByNameService.getActiveLocationList().subscribe(data => {
      this.locations=data;
    });
  }

  changeActiveState(location: Location | undefined) {
    this.findingByNameService.changeActiveState(location).subscribe(data=> {

    });
  }
}
