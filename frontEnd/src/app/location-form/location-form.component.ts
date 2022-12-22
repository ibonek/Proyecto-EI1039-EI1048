import {Component, OnInit} from '@angular/core';
import {FormControl} from '@angular/forms';
import {Observable, Subscription, take, tap} from 'rxjs';
import {startWith, map} from 'rxjs/operators';
import {HttpClient} from "@angular/common/http";
import {ActivatedRoute, Router} from "@angular/router";
import {FindingByNameService} from "../finding-by-name.service";
import {waitForAsync} from "@angular/core/testing";

@Component({
  selector: 'app-location-form',
  templateUrl: './location-form.component.html',
  styleUrls: ['./location-form.component.css']
})
export class LocationFormComponent implements OnInit {
  control = new FormControl();
  locations: string[] | undefined;
  filteredLocations: Observable<string[]> | undefined;
  locationName: string |undefined;
  confirmation: boolean| undefined;


  constructor(    private route: ActivatedRoute,
                  private router: Router,
                  private findingByNameService: FindingByNameService) {
    this.locationName="";
  }

  ngOnInit() {
    this.findingByNameService.giveCityList().subscribe(data=>{this.locations=data});
    this.filteredLocations = this.control.valueChanges.pipe(
      startWith(''),
      map(value => this._filter(value))
    );
  }

  private _filter(value: string): string[] {
    const filterValue = this._normalizeValue(value);
    // @ts-ignore
    return this.locations.filter(location => this._normalizeValue(location).includes(filterValue));
  }

  private _normalizeValue(value: string): string {
    return value.toLowerCase().replace(/\s/g, '');
  }

  onSubmit() {
    this.findingByNameService.save(this.locationName).subscribe();

  }



  setConfirmation(b: boolean){
    this.confirmation=b;
  }

}
