import {APIInformation} from "./apiinformation";

export class WeatherInformation extends APIInformation{
  temperature:String | undefined;

  weatherState:String | undefined;

  imageURL:String | undefined;

  humidity:String | undefined;
  wind:String | undefined;
}
