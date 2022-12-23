import {APIInformation} from "./apiinformation";

export class WeatherInformation extends APIInformation{
  temperature!:string;

  weatherState!:string;

  humidity!:string;

  wind!:string;
}
