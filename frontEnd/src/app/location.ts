import {Coordinates} from "./coordinates";

export class Location {
  name!: string
  coordinates!: Coordinates
  isActive!: boolean


  public toString(): string{

    return this.name+ '( '+this.coordinates.lat +', '+this.coordinates.lon+')';
  }

}
