import {Component, OnInit} from '@angular/core';
import {FormControl} from '@angular/forms';
import {Observable} from 'rxjs';
import {startWith, map} from 'rxjs/operators';
import {HttpClient} from "@angular/common/http";
import {ActivatedRoute, Router} from "@angular/router";
import {FindingByNameService} from "../finding-by-name.service";

@Component({
  selector: 'app-location-form',
  templateUrl: './location-form.component.html',
  styleUrls: ['./location-form.component.css']
})
export class LocationFormComponent implements OnInit {
  control = new FormControl();
  locations: string[] = ['Londres', 'Valencia', 'Madrid', 'Paris'];
  filteredLocations: Observable<string[]> | undefined;
  locationName: string;

  constructor(private findingByNameService: FindingByNameService  ) {
    this.locationName="";
  }

  ngOnInit() {
    this.filteredLocations = this.control.valueChanges.pipe(
      startWith(''),
      map(value => this._filter(value))
    );
    this.findingByNameService.findAll().subscribe(data =>{

    })

  }

  private _filter(value: string): string[] {
    const filterValue = this._normalizeValue(value);
    return this.locations.filter(location => this._normalizeValue(location).includes(filterValue));
  }

  private _normalizeValue(value: string): string {
    return value.toLowerCase().replace(/\s/g, '');
  }

    onSubmit() {
      alert(this.locationName)
      this.findingByNameService.save(this.locationName).subscribe();
    }




}
