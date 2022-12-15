import {Coordinates} from "./coordinates";

export class Location {
  name!: string
  coordinates!: Coordinates


  public toString(): string{

    return this.name+ '( '+this.coordinates.lat +', '+this.coordinates.lon+')';
  }

}
