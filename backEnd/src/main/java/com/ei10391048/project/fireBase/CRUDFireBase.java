package com.ei10391048.project.fireBase;

import com.ei10391048.project.exception.IncorrectLocationException;
import com.ei10391048.project.exception.NotSavedException;
import com.ei10391048.project.modelo.Coordinates;
import com.ei10391048.project.modelo.Location;
import com.ei10391048.project.modelo.api.API;
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
            List<Location>locations=new ArrayList<>();
            for (QueryDocumentSnapshot document:documents){
                Location location=new Location();
                location.setName((String) document.getData().get("name"));
                Coordinates coordinates=new Coordinates();
                coordinates.setLat((Double) document.getData().get("latitude"));
                coordinates.setLon((Double) document.getData().get("longitude"));
                location.setCoordinates(coordinates);
                locations.add(location);
            }
            return locations;
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
    public Location getLocation(Location location) {
        if (location==null){
            return null;
        }
        QueryDocumentSnapshot document;
        document = getLocationDocument(location);
        if (document==null){
            return null;
        }
        Location location1 = new Location();
        location1.setName((String) document.getData().get("name"));
        Coordinates coordinates = new Coordinates();
        coordinates.setLat((Double) document.getData().get("latitude"));
        coordinates.setLon((Double) document.getData().get("longitude"));
        location1.setActive((Boolean) document.getData().get("active"));
        location1.setCoordinates(coordinates);
        return location1;
    }

    public void deleteLocations() throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> future = db.collection("Location").get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
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
            docAPI.put("information",api.getInformation());
            docAPI.put("apiKey",api.getApiKey());
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
        api1.setApiKey((String) document.getData().get("apiKey"));
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
}
