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
  locations: string[] | undefined;
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
    this.getMyLocations()

    await this.findingByNameService.giveCityList().subscribe(data => {
      this.locations=data;
      this.filteredLocations = this.control.valueChanges.pipe(
        startWith(''),
        map(value => this._filter(value))
      );
    });


  }

  private getMyLocations(){
    this.findingByNameService.getActiveLocationList().subscribe(data => {
      for (let i=0; i<data.length; i++){
        this.mylocations[i] = data[i].name
      }

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

  onSubmit() {
    this.findingByNameService.getActiveLocationList().subscribe(data =>{
      for (let i= 0;i<data.length;i++){
        if (data[i].name === this.locationName){
          this.tinyFailAlert("Your location "+this.locationName+" has been added previously");
          return;
        }
      }
      this.findingByNameService.save(this.locationName).subscribe(data=> {
        this.findingByNameService.giveConfirmation().subscribe(confirmation=> {
          if (confirmation){
            this.tinySuccessAlert();
            this.mylocations[this.mylocations.length] = this.locationName;
          } else {
            this.tinyFailAlert("Your location "+this.locationName+" does not exist")
          }


        });
      });

    });




  }



  goToList(){
    this.router.navigate(['/locationList']);
  }

  tinySuccessAlert() {
      //Swal.fire('Good job!','Your location '+this.locationName+ " has been added!", "success");
      Swal.fire({
        title: 'Good job!',
        text: 'Your location '+this.locationName+" has been added!",
        icon: 'success',
        showCancelButton: false,
        confirmButtonColor: '#c2185b',
        confirmButtonText: 'Ok'
      }).then((result =>{
        if (result.isConfirmed){
          window.location.reload()
        }
      }))

  }

  tinyFailAlert(reason: string){
    Swal.fire({
      title: 'Ooops',
      text: reason,
      icon: 'error',
      confirmButtonColor: '#c2185b',
      confirmButtonText: 'Ok'
    })

  }

}
