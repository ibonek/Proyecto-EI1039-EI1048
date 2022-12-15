package com.ei10391048.project.modelo.api;

import com.ei10391048.project.modelo.information.APIInformation;
import com.ei10391048.project.modelo.information.NewsInformation;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class NewsAPI extends API{

    private final List<APIInformation> information;

    private final int numberOfNews = 15;

    private String locationName;
    private JSONArray news;

    public NewsAPI() {
        name = APIsNames.NEWS.getApiName();
        apiKey = "b851a5f3-39a4-4999-82ca-6a153635334f";
        information = super.information;
        for (int i=0;i<numberOfNews;i++){
            information.add(new NewsInformation());
        }
    }

    @Override
    void insertAPIName() {
        for (APIInformation n: information){
            n.setApiName(name);
        }
    }

    @Override
    void insertLocationName() {
        for (APIInformation n: information){
            n.setLocationName(locationName);
        }

    }

    @Override
    void insertImageURL() {
        JSONObject json;
        for (int i=0; i<numberOfNews;i++){
            NewsInformation info = (NewsInformation) information.get(i);
            json = new JSONObject(news.get(i).toString());
            String url = json.get("image").toString();
            if (url.equalsIgnoreCase("NONE"))
                info.setImageURL(null);
            else
                info.setImageURL(url);
        }

    }

    @Override
    void insertDate() {
        JSONObject json;
        for (int i=0; i<numberOfNews;i++) {
            NewsInformation info = (NewsInformation) information.get(i);
            json = new JSONObject(news.get(i).toString());
            info.setDate(json.getString("dateTime"));

        }
    }

    @Override
    boolean apiCall(String locationName) {
        HttpClient client= HttpClient.newHttpClient();
        HttpRequest request= HttpRequest.newBuilder()
                .uri(URI.create("https://www.newsapi.ai/api/v1/article/getArticles?query=%7B%22%24query%22%3A%7B%22%24and%22%3A%5B%7B%22conceptUri%22%3A%22http%3A%2F%2Fen.wikipedia.org%2Fwiki%2F"+locationName+"%22%7D%2C%7B%22keyword%22%3A%22"+locationName+"%22%2C%22keywordLoc%22%3A%22title%22%7D%5D%7D%2C%22%24filter%22%3A%7B%22forceMaxDataTimeWindow%22%3A%2231%22%7D%7D&resultType=articles&articlesSortBy=date&articlesCount="+numberOfNews+"&articleBodyLen=-1&apiKey="+apiKey+"&callback=JSON_CALLBACK"))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject json = new JSONObject(response.body().substring(14));

            news = new JSONArray( json.getJSONObject("articles").getJSONArray("results"));
            this.locationName=locationName;

            return true;

        } catch (IOException | InterruptedException ex){
            return false;
        }
    }


    void insertAuthor(){
        JSONObject json;
        for (int i=0; i<numberOfNews;i++) {
            NewsInformation info = (NewsInformation) information.get(i);
            json = new JSONObject(news.get(i).toString());
            info.setAuthor(json.getJSONObject("source").getString("title"));

        }
    }

    void insertTitle(){
        JSONObject json;
        for (int i=0; i<numberOfNews;i++) {
            NewsInformation info = (NewsInformation) information.get(i);
            json = new JSONObject(news.get(i).toString());
            info.setTitle(json.getString("title"));

        }
    }

    void  insertDescription(){
        JSONObject json;
        for (int i=0; i<numberOfNews;i++) {
            NewsInformation info = (NewsInformation) information.get(i);
            json = new JSONObject(news.get(i).toString());
            info.setDescription(json.getString("body").split("\n")[0]);

        }
    }
    void insertNewsUrl(){
        JSONObject json;
        for (int i=0; i<numberOfNews;i++) {
            NewsInformation info = (NewsInformation) information.get(i);
            json = new JSONObject(news.get(i).toString());
            info.setNewsURL(json.getString("url"));

        }
    }
    @Override
    void insertBodyData() {
        insertAuthor();
        insertTitle();
        insertDescription();
        insertNewsUrl();

    }
}
