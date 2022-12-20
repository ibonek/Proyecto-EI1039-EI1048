package com.ei10391048.project.fireBase;

import com.ei10391048.project.exception.NotSavedException;
import com.ei10391048.project.modelo.Coordinates;
import com.ei10391048.project.modelo.Location;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;

import java.util.*;
import java.util.concurrent.ExecutionException;

public class CRUDFireBase {
    private static Firestore db=null;

    public CRUDFireBase() {
        if (db==null) {
            FireBaseConnection fireBaseConnection = new FireBaseConnection();
            db = fireBaseConnection.initializeFireBase();
        }
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

    public List<Location> getLocations(){
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
            throw new RuntimeException(e);
        }
    }

    public Location getLocation(Location location) throws ExecutionException, InterruptedException {
        if (location==null){
            return null;
        }
        ApiFuture<QuerySnapshot> future = db.collection("Location").get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            if (document.getData().get("name").equals(location.getName())&&
                    document.getData().get("latitude").equals(location.getCoordinates().getLat())&&
                    document.getData().get("longitude").equals(location.getCoordinates().getLon())) {
                Location location1 = new Location();
                location1.setName((String) document.getData().get("name"));
                Coordinates coordinates = new Coordinates();
                coordinates.setLat((Double) document.getData().get("latitude"));
                coordinates.setLon((Double) document.getData().get("longitude"));
                location1.setCoordinates(coordinates);
                return location1;
            }
        }
        return null;
    }

    public void deleteLocations() throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> future = db.collection("Location").get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            db.collection("Location").document(document.getId()).delete();
        }
    }
}
