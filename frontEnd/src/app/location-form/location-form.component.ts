import {Component, OnInit} from '@angular/core';
import {FormControl} from '@angular/forms';
import {Observable, Subscription, take, tap} from 'rxjs';
import {startWith, map} from 'rxjs/operators';
import {HttpClient} from "@angular/common/http";
import {ActivatedRoute, Router} from "@angular/router";
import {FindingByNameService} from "../finding-by-name.service";
import {waitForAsync} from "@angular/core/testing";
import Swal from "sweetalert2";
import {Location} from "../location";

@Component({
  selector: 'app-location-form',
  templateUrl: './location-form.component.html',
  styleUrls: ['./location-form.component.css']
})
export class LocationFormComponent implements OnInit {
  control = new FormControl();
  locations!: string[];
  filteredLocations: Observable<string[]> | undefined;
  locationName!: string;
  mylocations !: string[]


  constructor(    private route: ActivatedRoute,
                  private router: Router,
                  private findingByNameService: FindingByNameService) {
    this.locationName="";
    this.mylocations = [];
  }

  async ngOnInit() {
    this.findingByNameService.giveCityList().subscribe(data => {
      this.locations = data;


      this.filteredLocations = this.control.valueChanges.pipe(
        startWith(''),
        map(value => this._filter(value))
      );
    });


  }


  private _filter(value: string): string[] {
    const filterValue = this._normalizeValue(value);
    // @ts-ignore
    return this.locations.filter(location => this._normalizeValue(location).includes(filterValue));
  }

  private _normalizeValue(value: string): string {
    return value.toLowerCase().replace(/\s/g, '');
  }

  public async currentLocation() {
    navigator.geolocation.watchPosition(async (position) => {
      let mylocation = position.coords.latitude.toFixed(4) + ', ' + position.coords.longitude.toFixed(4);

      await this.findingByNameService.getLocationList().subscribe(data =>  {
        for (let i=0;i<data.length;i++){
          if (data[i].name === mylocation || data[i].coordinates.lat+", "+data[i].coordinates.lon === mylocation){
            this.tinyFailAlert("Your current location has been added previously");
            return;
          }
          }
        this.tinyQuestionAlert(mylocation);
        return;

      });


  });
  }

  private async submitLocation(){
    this.findingByNameService.save(this.locationName).subscribe(data => {
      this.findingByNameService.giveConfirmation().subscribe(confirmation => {
        if (confirmation) {
          this.tinySuccessAlert();
        } else {
          this.tinyFailAlert("Your location " + this.locationName + " does not exist");
        }
      });
    });
  }
  async onSubmit() {
    this.findingByNameService.getLocationList().subscribe(data => {
      for (let i = 0; i < data.length; i++) {
        if (data[i].name.toUpperCase() === this.locationName.trim().toUpperCase() || data[i].coordinates.lat + ", " + data[i].coordinates.lon === this.locationName) {
          this.tinyFailAlert("Your location "+this.locationName+ " has been added previously");
          return;
        }
      }
      this.submitLocation();
    });
  }



  goToList(){
    this.router.navigate(['/locationList']);
  }

  async tinySuccessAlert() {
      Swal.fire({
        title: 'Good job!',
        text: 'Your location '+this.locationName+" has been added!",
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

  async tinyQuestionAlert(mylocation: string){
    Swal.fire({
      title: 'Do you want to register your current location?',
      icon: 'question',
      confirmButtonColor: '#c2185b',
      showCancelButton: true,
      confirmButtonText: 'Yes',
      cancelButtonText: 'No',
      cancelButtonColor: '#ff0421',
      reverseButtons: true
    }).then(( result => {
      if (result.isConfirmed) {
        this.locationName = mylocation;
        this.submitLocation();
      }
    }))
  }

  async tinyFailAlert(reason: string){
    await Swal.fire({
      title: 'Ooops',
      text: reason,
      icon: 'error',
      confirmButtonColor: '#c2185b',
      confirmButtonText: 'Ok'
    })

  }

}
