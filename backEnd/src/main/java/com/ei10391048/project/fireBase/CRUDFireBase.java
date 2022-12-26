package com.ei10391048.project.fireBase;

import com.ei10391048.project.exception.IncorrectLocationException;
import com.ei10391048.project.exception.NotExistingAPIException;
import com.ei10391048.project.exception.NotSavedException;
import com.ei10391048.project.modelo.Coordinates;
import com.ei10391048.project.modelo.Location;
import com.ei10391048.project.modelo.api.API;
import com.ei10391048.project.modelo.api.NewsAPI;
import com.ei10391048.project.modelo.api.OpenWeather;
import com.ei10391048.project.modelo.api.TicketMaster;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;

import java.util.*;
import java.util.concurrent.ExecutionException;

public class CRUDFireBase {
    private static Firestore db;

    public CRUDFireBase() {
        db = FireBaseConnection.getInstance();
    }

    public void addLocation(Location location) throws NotSavedException {
        try {
            Map<String,Object>docLocation=new HashMap<>();
            docLocation.put("id", UUID.randomUUID().toString());
            docLocation.put("name",location.getName());
            docLocation.put("latitude",location.getCoordinates().getLat());
            docLocation.put("longitude",location.getCoordinates().getLon());
            docLocation.put("active",true);
            OpenWeather openWeather = (OpenWeather) location.getApiManager().getApiList().get(0);
            docLocation.put("open_weather",openWeather.getName());
            docLocation.put("open_weather_active",openWeather.getIsActive());
            TicketMaster ticketMaster = (TicketMaster) location.getApiManager().getApiList().get(1);
            docLocation.put("ticket_master",ticketMaster.getName());
            docLocation.put("ticket_master_active",ticketMaster.getIsActive());
            NewsAPI newsAPI = (NewsAPI) location.getApiManager().getApiList().get(2);
            docLocation.put("news_api",newsAPI.getName());
            docLocation.put("news_api_active",newsAPI.getIsActive());

            ApiFuture<WriteResult> future=db.collection("Location").document(String.valueOf(UUID.randomUUID())).set(docLocation);
            future.get().getUpdateTime();
        } catch (InterruptedException | ExecutionException | NullPointerException e) {
            throw new NotSavedException();
        }
    }

    public List<Location> getLocations() throws IncorrectLocationException {
        try {
            ApiFuture<QuerySnapshot> future=db.collection("Location").get();
            List<QueryDocumentSnapshot> documents=future.get().getDocuments();
            List<Location> locations=new LinkedList<>();
            for (QueryDocumentSnapshot document:documents){
                Location location=new Location();
                location.setName((String) document.getData().get("name"));
                Coordinates coordinates=new Coordinates();
                coordinates.setLat((Double) document.getData().get("latitude"));
                coordinates.setLon((Double) document.getData().get("longitude"));
                location.setCoordinates(coordinates);
                location.setActive((Boolean) document.getData().get("active"));
                locations.add(location);
            }
            return locations;
        } catch (InterruptedException | ExecutionException e) {
            throw new IncorrectLocationException();
        }
    }

    public Map<Location,List<API>> getAPILocations() throws IncorrectLocationException {
        try {
            ApiFuture<QuerySnapshot> future=db.collection("Location").get();
            List<QueryDocumentSnapshot> documents=future.get().getDocuments();
            Map<Location,List<API>>locationAPIs=new HashMap<>();
            for (QueryDocumentSnapshot document:documents){
                Location location=new Location();
                location.setName((String) document.getData().get("name"));
                Coordinates coordinates=new Coordinates();
                coordinates.setLat((Double) document.getData().get("latitude"));
                coordinates.setLon((Double) document.getData().get("longitude"));
                location.setCoordinates(coordinates);
                List<API>apiList=new ArrayList<>();
                OpenWeather openWeather=new OpenWeather();
                openWeather.setName((String) document.getData().get("open_weather"));
                openWeather.setActive((Boolean) document.getData().get("open_weather_active"));
                apiList.add(openWeather);
                TicketMaster ticketMaster=new TicketMaster();
                ticketMaster.setName((String) document.getData().get("ticket_master"));
                ticketMaster.setActive((Boolean) document.getData().get("ticket_master_active"));
                apiList.add(ticketMaster);
                NewsAPI newsAPI=new NewsAPI();
                newsAPI.setName((String) document.getData().get("news_api"));
                newsAPI.setActive((Boolean) document.getData().get("news_api_active"));
                apiList.add(newsAPI);
                locationAPIs.put(location,apiList);
            }
            return locationAPIs;
        } catch (InterruptedException | ExecutionException e) {
            throw new IncorrectLocationException();
        }
    }

    private QueryDocumentSnapshot getLocationDocument(Location location) {
        if (location==null){
            return null;
        }
        ApiFuture<QuerySnapshot> future=db.collection("Location").get();
        List<QueryDocumentSnapshot> documents;
        try {
            documents = future.get().getDocuments();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        for (QueryDocumentSnapshot document:documents){
            if (document.getData().get("name").equals(location.getName()) &&
                    document.getData().get("latitude").equals(location.getCoordinates().getLat())&&
                    document.getData().get("longitude").equals(location.getCoordinates().getLon())){
                return document;
            }
        }
        return null;
    }
    public Map<String,List<API>> getAPILocation(Location location) {
        if (location==null){
            return null;
        }
        QueryDocumentSnapshot document;
        document = getLocationDocument(location);
        if (document==null){
            return null;
        }
        String name=(String) document.getData().get("name");
        List<API> apiList = new ArrayList<>();
        OpenWeather openWeather = new OpenWeather();
        openWeather.setName((String) document.getData().get("open_weather"));
        openWeather.setActive((Boolean) document.getData().get("open_weather_active"));
        apiList.add(openWeather);
        TicketMaster ticketMaster = new TicketMaster();
        ticketMaster.setName((String) document.getData().get("ticket_master"));
        ticketMaster.setActive((Boolean) document.getData().get("ticket_master_active"));
        apiList.add(ticketMaster);
        NewsAPI newsAPI = new NewsAPI();
        newsAPI.setName((String) document.getData().get("news_api"));
        newsAPI.setActive((Boolean) document.getData().get("news_api_active"));
        apiList.add(newsAPI);
        Map<String, List<API>> locationAPIs = new HashMap<>();
        locationAPIs.put(name, apiList);
        return locationAPIs;
    }

    public Location getLocation(Location location) {
        ApiFuture<QuerySnapshot> future=db.collection("Location").get();
        List<QueryDocumentSnapshot> documents;
        try {
            documents = future.get().getDocuments();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        for (QueryDocumentSnapshot document:documents){
            if (document.getData().get("name").equals(location.getName()) &&
                    document.getData().get("latitude").equals(location.getCoordinates().getLat())&&
                    document.getData().get("longitude").equals(location.getCoordinates().getLon())){
                Location location1=new Location();
                location1.setName((String) document.getData().get("name"));
                Coordinates coordinates=new Coordinates();
                coordinates.setLat((Double) document.getData().get("latitude"));
                coordinates.setLon((Double) document.getData().get("longitude"));
                location1.setCoordinates(coordinates);
                location1.setActive((Boolean) document.getData().get("active"));
                return location1;
            }
        }
        return null;
    }

    public void deleteLocations() {
        ApiFuture<QuerySnapshot> future = db.collection("Location").get();
        List<QueryDocumentSnapshot> documents;
        try {
            documents = future.get().getDocuments();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        for (QueryDocumentSnapshot document : documents) {
            db.collection("Location").document(document.getId()).delete();
        }
    }

    public void changeLocationStatus(Location location) throws NotSavedException {
        QueryDocumentSnapshot document;
        document = getLocationDocument(location);
        if (document==null){
            throw new NotSavedException();
        }
        document.getReference().update("active",!location.getIsActive());
    }

    public void addAPI(API api) throws NotSavedException {
        try {
            Map<String,Object>docAPI=new HashMap<>();
            docAPI.put("id", UUID.randomUUID().toString());
            docAPI.put("name",api.getName());
            docAPI.put("isActive", api.getIsActive());

            ApiFuture<WriteResult> future=db.collection("API").document(String.valueOf(UUID.randomUUID())).set(docAPI);
            future.get().getUpdateTime();
        } catch (InterruptedException | ExecutionException | NullPointerException e) {
            throw new NotSavedException();
        }

    }

    public API getAPI(API api) {
        if (api==null){
            return null;
        }
        QueryDocumentSnapshot document;
        document = getAPIDocument(api);
        if (document==null){
            return null;
        }
        API api1 = api;
        api1.setName((String) document.getData().get("name"));
        api1.setActive((Boolean) document.getData().get("isActive"));
        return api1;
    }

    private QueryDocumentSnapshot getAPIDocument(API api) {
        if (api==null){
            return null;
        }
        ApiFuture<QuerySnapshot> future;
        try {
            future = db.collection("API").get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                if (document.getData().get("name").equals(api.getName())) {
                    return document;
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void changeAPIStatus(API api) throws NotSavedException {
        QueryDocumentSnapshot document;
        document = getAPIDocument(api);
        if (document==null){
            throw new NotSavedException();
        }
        document.getReference().update("isActive",!api.getIsActive());
    }

    public void changeAPILocationStatus(API api, Location location) throws NotSavedException {
        QueryDocumentSnapshot document;
        document = getLocationDocument(location);
        if (document==null){
            throw new NotSavedException();
        }
        switch (api.getAPIName()) {
            case "NewsAPI" -> document.getReference().update("news_api_active", !api.getIsActive());
            case "OpenWeather" -> document.getReference().update("open_weather_active", !api.getIsActive());
            case "TicketMaster" -> document.getReference().update("ticket_master_active", !api.getIsActive());
        }
    }



    public void deleteAPIs() {
        try {
            ApiFuture<QuerySnapshot> future = db.collection("API").get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                db.collection("API").document(document.getId()).delete();
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public List<API> getAPIs() throws NotExistingAPIException {
        try {
            ApiFuture<QuerySnapshot> future = db.collection("API").get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            if (documents.isEmpty()) {
                throw new NotExistingAPIException();
            }
            List<API> apis = new ArrayList<>();
            for (QueryDocumentSnapshot document : documents) {
                String name = (String) document.getData().get("name");
                API api = switch (name) {
                    case "OpenWeather" -> new OpenWeather();
                    case "TicketMaster" -> new TicketMaster();
                    case "NewsAPI" -> new NewsAPI();
                    default -> throw new NotExistingAPIException();
                };
                api.setName(name);
                api.setActive((Boolean) document.getData().get("isActive"));
                apis.add(api);
            }
            return apis;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteLocation(Location location) throws IncorrectLocationException{
        QueryDocumentSnapshot document;
        document = getLocationDocument(location);
        if (document==null){
            throw new IncorrectLocationException();
        }
        document.getReference().delete();
    }
}
