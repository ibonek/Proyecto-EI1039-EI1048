package com.ei10391048.project.fireBase;

import com.ei10391048.project.exception.IncorrectLocationException;
import com.ei10391048.project.exception.NotSavedException;
import com.ei10391048.project.modelo.Coordinates;
import com.ei10391048.project.modelo.Location;
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

    private QueryDocumentSnapshot getDocument(Location location) throws ExecutionException, InterruptedException {
        if (location==null){
            return null;
        }
        ApiFuture<QuerySnapshot> future=db.collection("Location").get();
        List<QueryDocumentSnapshot> documents=future.get().getDocuments();
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
        try {
            document = getDocument(location);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
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

    public void changeStatus(Location location) throws NotSavedException {
        QueryDocumentSnapshot document;
        try {
            document = getDocument(location);
        } catch (ExecutionException | InterruptedException e) {
            throw new NotSavedException();
        }
        if (document==null){
            throw new NotSavedException();
        }
        document.getReference().update("active",!location.getIsActive());
    }

    public void deleteLocation(Location location) throws IncorrectLocationException{
    }
}
