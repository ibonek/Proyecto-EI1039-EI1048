import {APIInformation} from "./apiinformation";

export class NewsInformation extends APIInformation{
  author!: string;
  title!: string;
  description!: string;
}
