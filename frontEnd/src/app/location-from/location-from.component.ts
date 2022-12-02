import {Component, OnInit} from '@angular/core';
import {FormControl} from '@angular/forms';
import {Observable} from 'rxjs';
import {startWith, map} from 'rxjs/operators';
/*
@Component({
  selector: 'app-location-from',
  templateUrl: './location-from.component.html',
  styleUrls: ['./location-from.component.css']
})
export class LocationFromComponent implements OnInit {
  control = new FormControl();
  locations: string[] = ['Londres', 'Valencia', 'Madrid', 'Paris', 'Castellón'];
  filteredLocations: Observable<string[]> | undefined;

  ngOnInit() {
    this.filteredLocations = this.control.valueChanges.pipe(
      startWith(''),
      map(value => this._filter(value))
    );
  }

  private _filter(value: string): string[] {
    const filterValue = this._normalizeValue(value);
    return this.locations.filter(location => this._normalizeValue(location).includes(filterValue));
  }

  private _normalizeValue(value: string): string {
    return value.toLowerCase().replace(/\s/g, '');
  }
}

 */
