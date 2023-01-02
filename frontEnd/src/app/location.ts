import {Coordinates} from "./coordinates";
import {ApiManager} from "./api-manager";

export class Location {
  name!: string
  alias!: string
  coordinates!: Coordinates
  isActive!: boolean
  apiManager!: ApiManager

}
