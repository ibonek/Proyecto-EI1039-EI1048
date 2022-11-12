import { Component, OnInit } from '@angular/core';
import {Coordenadas} from "../coordenadas";
import {ActivatedRoute, Router} from "@angular/router";
import {CoordenadasService} from "../coordenadas.service";

class UserService {
}

@Component({
  selector: 'app-user-form',
  templateUrl: './coordenadas-form.component.html',
  styleUrls: ['./coordenadas-form.component.css']
})
export class CoordenadasFormComponent{

  coordenadas: Coordenadas;
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private coordenadasService: CoordenadasService)
   {
  this.coordenadas = new Coordenadas();
}

  onSubmit(){
    this.coordenadasService.save(this.coordenadas).subscribe(result => this.goToCoordenadasList());
  }

  goToCoordenadasList(){
    this.router.navigate(['/coordenadas']);
  }

}
