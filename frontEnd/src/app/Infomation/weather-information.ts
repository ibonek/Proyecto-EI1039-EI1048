import {APIInformation} from "./apiinformation";

export class WeatherInformation extends APIInformation{
  temperature:string | undefined;

  weatherState:string | undefined;

  humidity:string | undefined;

  wind:string | undefined;
}
