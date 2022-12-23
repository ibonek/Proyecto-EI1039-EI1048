import {Component, OnInit} from '@angular/core';
import {map, startWith} from "rxjs/operators";
import {FindingByNameService} from "../finding-by-name.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Location} from "../location";
import {FormArray, FormBuilder, FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-location-list',
  templateUrl: './location-list.component.html',
  styleUrls: ['./location-list.component.css']
})
export class LocationListComponent implements OnInit {

  locations!: Location[]
  pos!: number
  froms = this.fb.group({
    aliases: this.fb.array([])
  });

  constructor( private route: ActivatedRoute,
               private router: Router,
               private findingByNameService: FindingByNameService,
               private fb: FormBuilder) {
  }

  ngOnInit(){
    this.findingByNameService.getActiveLocationList().subscribe(data => {
      this.locations=data;
      for(let i=0; i<this.locations.length; i++){
        this.aliases.push(this.fb.control(''));
      }
    });
  }
  get aliases() {
    return this.froms.get('aliases') as FormArray;
  }

  onSubmit(){
    this.findingByNameService.changeAlias(this.locations[this.pos].name,this.aliases.value[this.pos]).subscribe( data =>{})
  }

  changeActiveState(location: Location | undefined) {
    this.findingByNameService.changeActiveState(location).subscribe(data=> {});
  }

  setPos(i: number){
    this.pos = i
  }


}
