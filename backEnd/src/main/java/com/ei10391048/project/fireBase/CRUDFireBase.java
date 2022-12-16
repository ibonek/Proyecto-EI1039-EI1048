package com.ei10391048.project.fireBase;

import com.ei10391048.project.exception.NotSavedException;
import com.ei10391048.project.modelo.Location;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
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
            docLocation.put("latitud",location.getCoordinates().getLat());
            docLocation.put("longitud",location.getCoordinates().getLon());
            docLocation.put("active",true);

            ApiFuture<WriteResult> future=db.collection("Location").document(String.valueOf(UUID.randomUUID())).set(docLocation);
            future.get().getUpdateTime();
        } catch (InterruptedException | ExecutionException | NullPointerException e) {
            throw new NotSavedException();
        }
    }
}
