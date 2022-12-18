package com.ei10391048.project.modelo.api;

import com.ei10391048.project.modelo.information.APIInformation;
import com.ei10391048.project.modelo.information.EventInformation;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class TicketMaster extends API{


    private JSONArray events;

    private final List<APIInformation> information;

    private final int numberOfEvents = 15;
    public TicketMaster(){
        apiKey = "0F9NRxxNHHARWvlAgkokiKrKFYVqEqM8";
        name = APIsNames.EVENTS.getApiName();
        information = super.information;
        for (int i=0 ; i<numberOfEvents;i++){
            information.add(new EventInformation());
        }
    }
    @Override
    void insertAPIName() {
        for (APIInformation event: information){
            event.setApiName(name);
        }
    }

    private void insertEventName(){
        JSONObject json;
        for (int i=0; i<numberOfEvents;i++){
            EventInformation info = (EventInformation) information.get(i);

            json = new JSONObject(events.get(i).toString());
            info.setEventName(json.getString("name"));
        }
    }
    @Override
    void insertLocationName() {
        JSONObject event;
        for (int i=0; i<numberOfEvents;i++){
            EventInformation info = (EventInformation) information.get(i);

            event = new JSONObject(events.get(i).toString());
            JSONObject venues = new JSONObject(event.getJSONObject("_embedded").getJSONArray("venues").get(0).toString());
            info.setLocationName(venues.getJSONObject("city").getString("name"));
            info.setAddress(venues.getJSONObject("address").getString("line1"));
            info.setPlace(venues.getString("name"));
        }
    }

    @Override
    void insertImageURL() {
        JSONArray json;
        for (int i=0; i<numberOfEvents;i++){
            EventInformation info = (EventInformation) information.get(i);
            JSONObject object =new JSONObject(events.get(i).toString());
            json = object.getJSONArray("images");
            info.setImageURL(new JSONObject(json.get(0).toString()).getString("url"));
        }
    }

    @Override
    void insertDate() {
        JSONObject event;
        for (int i=0; i<numberOfEvents;i++) {
            event = new JSONObject(events.get(i).toString());
            JSONObject sales = event.getJSONObject("sales").getJSONObject("public");
            String date = sales.getString("endDateTime");
            date = date.substring(0,date.length()-1);
            information.get(i).setDate(date);
        }
    }

    @Override
    boolean apiCall(String locationName) {
        // &page=${contador}

        HttpClient client= HttpClient.newHttpClient();
        HttpRequest request= HttpRequest.newBuilder()
                .uri(URI.create("https://app.ticketmaster.com/discovery/v2/events.json?apikey="+apiKey+"&locale=*&size="+numberOfEvents+"&sort=relevance,desc&city="+locationName))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject json = new JSONObject(response.body());
            events = new JSONArray( json.getJSONObject("_embedded").getJSONArray("events"));

            return true;

        } catch (IOException | InterruptedException | JSONException ex){
            return false;
        }
    }


    @Override
    void insertBodyData() {
        insertEventName();
    }


}
