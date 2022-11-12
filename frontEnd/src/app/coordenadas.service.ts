import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import {Coordenadas} from "./coordenadas";
import {Observable} from "rxjs";

@Injectable()
export class CoordenadasService {
  private coordenadasUrl: string;

  constructor(private http: HttpClient) {
    this.coordenadasUrl = 'http://localhost:8080/coordenadas';
  }
  public findAll(): Observable<Coordenadas[]> {
    return this.http.get<Coordenadas[]>(this.coordenadasUrl);
  }

  public save(coordenadas: Coordenadas) {
    alert(coordenadas.latitud+" "+coordenadas.longitud+ " "+coordenadas.id);
    return this.http.post<Coordenadas>(this.coordenadasUrl, coordenadas);
  }
}
