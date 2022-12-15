import {APIInformation} from "./apiinformation";

export class NewsInformation extends APIInformation{
  author: string | undefined;
  title: string | undefined;
  description: string | undefined;
  newsUrl: string | undefined;
}
