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
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class NewsAPI extends API{

    private final List<APIInformation> information;

    private final int numberOfNews = 15;

    private String locationName;
    private JSONArray news;

    private final List<Integer> nonRepeatedIndex;


    public NewsAPI() {
        name = APIsNames.NEWS.getApiName();
        apiKey = "b851a5f3-39a4-4999-82ca-6a153635334f";
        information = super.information;
        nonRepeatedIndex = new LinkedList<>();

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
        int j=0;
        for (int i: nonRepeatedIndex){
            NewsInformation info = (NewsInformation) information.get(j);
            json = new JSONObject(news.get(i).toString());
            String url = json.get("image").toString();
            if (url.equalsIgnoreCase("NONE"))
                info.setImageURL(null);
            else
                info.setImageURL(url);
            j++;
        }

    }

    @Override
    void insertDate() {
        JSONObject json;
        int j=0;
        for (int i: nonRepeatedIndex) {
            NewsInformation info = (NewsInformation) information.get(j);
            json = new JSONObject(news.get(i).toString());
            info.setDate(json.getString("dateTime"));
            j++;

        }
    }

    @Override
    boolean apiCall(String locationName) {
        HttpClient client= HttpClient.newHttpClient();
        HttpRequest request= HttpRequest.newBuilder()
                .uri(URI.create("https://www.newsapi.ai/api/v1/article/getArticles?query=%7B%22%24query%22%3A%7B%22%24and%22%3A%5B%7B%22%24and%22%3A%5B%7B%22conceptUri%22%3A%22http%3A%2F%2Fen.wikipedia.org%2Fwiki%2F"+locationName+"%22%7D%2C%7B%22keyword%22%3A%22"+locationName+"%22%2C%22keywordLoc%22%3A%22title%22%7D%5D%7D%2C%7B%22%24or%22%3A%5B%7B%22lang%22%3A%22eng%22%7D%2C%7B%22lang%22%3A%22spa%22%7D%2C%7B%22lang%22%3A%22cat%22%7D%5D%7D%5D%7D%2C%22%24filter%22%3A%7B%22forceMaxDataTimeWindow%22%3A%2231%22%7D%7D&resultType=articles&articlesSortBy=date&articlesCount="+numberOfNews+"&articleBodyLen=-1&apiKey="+apiKey+"&callback=JSON_CALLBACK"))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject json = new JSONObject(response.body().substring(14));

            news = new JSONArray( json.getJSONObject("articles").getJSONArray("results"));
            this.locationName=locationName;

            return true;

        } catch (IOException | InterruptedException ex){
            ex.printStackTrace();
            return false;
        }
    }


    void insertAuthor(){
        JSONObject json;
        int j=0;
        for (int i: nonRepeatedIndex) {
            NewsInformation info = (NewsInformation) information.get(j);
            json = new JSONObject(news.get(i).toString());
            info.setAuthor(json.getJSONObject("source").getString("title"));
            j++;
        }
    }

    void insertTitle(){
        if (nonRepeatedIndex.size()!=0){
            return;
        }
        JSONObject json;
        Set<String> repeatedSet = new HashSet<>();
        int i=0;
        for (Object n: news) {
            json = new JSONObject(n.toString());
            String title =json.getString("title");

            if (!repeatedSet.contains(title)){
                repeatedSet.add(title);
                NewsInformation info = new NewsInformation();
                info.setTitle(title);
                information.add(info);
                nonRepeatedIndex.add(i);
            }
            i++;
        }
    }


    void  insertDescription(){
        JSONObject json;
        int j=0;
        for (int i: nonRepeatedIndex) {
            NewsInformation info = (NewsInformation) information.get(j);
            json = new JSONObject(news.get(i).toString());
            String body = json.getString("body");
            int finalIndex = body.indexOf(' ',body.length()/4);

            String subBody =body.substring(0,finalIndex);
            info.setDescription(subBody+"...");
            j++;

        }
    }
    void insertNewsUrl(){
        JSONObject json;
        int j=0;
        for (int i: nonRepeatedIndex) {
            NewsInformation info = (NewsInformation) information.get(j);
            json = new JSONObject(news.get(i).toString());
            info.setNewsURL(json.getString("url"));
            j++;
        }
    }
    @Override
    void insertBodyData() {
        insertTitle();
        insertAuthor();
        insertDescription();
        insertNewsUrl();
    }
}
