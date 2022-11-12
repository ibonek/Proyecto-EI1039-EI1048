import { Component, OnInit } from '@angular/core';
import {Coordenadas} from "../coordenadas";
import {CoordenadasService} from "../coordenadas.service";

@Component({
  selector: 'app-user-list',
  templateUrl: './coordenadas-list.component.html',
  styleUrls: ['./coordenadas-list.component.css']
})
export class CoordenadasListComponent implements OnInit {

  coordenadas: Coordenadas[]| undefined;
  constructor(private coordenadasService: CoordenadasService) { }

  ngOnInit(): void {
    this.coordenadasService.findAll().subscribe(data=>{
      this.coordenadas = data;
    })
  }

}
