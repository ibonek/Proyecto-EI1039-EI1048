package com.ei10391048.project.fireBase;

import com.ei10391048.project.exception.*;
import com.ei10391048.project.modelo.Coordinates;
import com.ei10391048.project.modelo.Location;
import com.ei10391048.project.modelo.api.API;
import com.ei10391048.project.modelo.api.NewsAPI;
import com.ei10391048.project.modelo.api.OpenWeather;
import com.ei10391048.project.modelo.api.TicketMaster;
import com.ei10391048.project.modelo.user.User;
import com.ei10391048.project.modelo.user.UserFacade;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;

import java.util.*;
import java.util.concurrent.ExecutionException;

import static java.lang.Thread.sleep;

public class CRUDFireBase {
    private static Firestore db;

    private static CRUDFireBase crudFireBase;

    private CRUDFireBase() {
        db = FireBaseConnection.getInstance();
    }

    public static CRUDFireBase getInstance(){
        if (crudFireBase==null){
            crudFireBase = new CRUDFireBase();
            return crudFireBase;
        }else{
            return crudFireBase;
        }
    }

    public void addUser(String email) throws IncorrectUserException, InterruptedException {
        if (email == null) {
            throw new IncorrectUserException();
        }
        db.collection("users").document(email).set(Map.of("email", email));
    sleep(1000);

        addAPIs(email);
    }

    public UserFacade getUser(String email) {
        if (email==null){
            return null;
        }
        QueryDocumentSnapshot document;
        try {
            document = getUserDocument(email);
        } catch (IncorrectUserException e) {
            throw new RuntimeException(e);
        }
        if (document == null) {
            return null;
        }
        UserFacade user = new User();
        user.setEmail((String) document.getData().get("email"));
        return user;
    }

    public List<User> getUsers() {
        try {
            ApiFuture<QuerySnapshot> future = db.collection("users").get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            if (documents.isEmpty()) {
                return new ArrayList<>();
            }
            List<User> users = new ArrayList<>();
            for (QueryDocumentSnapshot document : documents) {
                User user = new User();
                user.setEmail((String) document.getData().get("email"));
                users.add(user);
            }
            return users;
        } catch (InterruptedException | ExecutionException e) {
            return new ArrayList<>();
        }
    }

    public void deleteUser(String email) throws IncorrectUserException {
        if (email==null){
            throw new IncorrectUserException();
        }
        QueryDocumentSnapshot document;
        try {
            document = getUserDocument(email);
        } catch (IncorrectUserException e) {
            throw new IncorrectUserException();
        }
        if (document==null){
            return;
        }
        deleteUserAPIs(email);
        deleteUserLocations(email);
        document.getReference().delete();
    }

    public void deleteUsers() throws IncorrectUserException {
        try {
            ApiFuture<QuerySnapshot> future = db.collection("users").get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            if (documents.isEmpty()) {
                return;
            }
            for (QueryDocumentSnapshot document : documents) {
                deleteUserLocations(document.getId());
                deleteUserAPIs(document.getId());
                document.getReference().delete();
                //FirebaseAuth.getInstance().deleteUser(document.getData().get("email").toString());
            }
        } catch (InterruptedException | ExecutionException /*| FirebaseAuthException*/ e) {
            throw new IncorrectUserException();
        }
    }

    private void addAPIs(String email){
        OpenWeather openWeather = new OpenWeather();
        Map<String,Object>docAPI=new HashMap<>();
        docAPI.put("name", openWeather.getName());
        docAPI.put("active",openWeather.getIsActive());

        Map<String,Object>docAPI2=new HashMap<>();
        TicketMaster ticketMaster = new TicketMaster();
        docAPI2.put("name", ticketMaster.getName());
        docAPI2.put("active",ticketMaster.getIsActive());

        Map<String,Object>docAPI3=new HashMap<>();
        NewsAPI newsAPI = new NewsAPI();
        docAPI3.put("name", newsAPI.getName());
        docAPI3.put("active",newsAPI.getIsActive());

        db.collection("users").document(email).collection("apis").document(openWeather.getName()).set(docAPI);
        db.collection("users").document(email).collection("apis").document(ticketMaster.getName()).set(docAPI2);
        db.collection("users").document(email).collection("apis").document(newsAPI.getName()).set(docAPI3);
    }

    private void addAPIs(String email, String locationName){
        OpenWeather openWeather = new OpenWeather();
        Map<String,Object>docAPI=new HashMap<>();
        docAPI.put("name", openWeather.getName());
        docAPI.put("active",openWeather.getIsActive());

        Map<String,Object>docAPI2=new HashMap<>();
        TicketMaster ticketMaster = new TicketMaster();
        docAPI2.put("name", ticketMaster.getName());
        docAPI2.put("active",ticketMaster.getIsActive());

        Map<String,Object>docAPI3=new HashMap<>();
        NewsAPI newsAPI = new NewsAPI();
        docAPI3.put("name", newsAPI.getName());
        docAPI3.put("active",newsAPI.getIsActive());

        db.collection("users").document(email).collection("locations").document(locationName).collection("apis").document(openWeather.getName()).set(docAPI);
        db.collection("users").document(email).collection("locations").document(locationName).collection("apis").document(ticketMaster.getName()).set(docAPI2);
        db.collection("users").document(email).collection("locations").document(locationName).collection("apis").document(newsAPI.getName()).set(docAPI3);
    }

    public void addUserLocation(Location location, String email) throws NotSavedException, AlreadyExistentLocationException, ExecutionException, InterruptedException {
        if (location == null || email == null) {
            throw new NotSavedException();
        }
        if (getUser(email) == null) {
            return;
        }
        List<QueryDocumentSnapshot> documents=db.collection("users").document(email).collection("locations").get().get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            if (document.getData().get("name").equals(location.getName())){
                throw new AlreadyExistentLocationException();
            }
        }
        Map<String,Object>docLocation=new HashMap<>();
        docLocation.put("name",location.getName());
        docLocation.put("latitude",location.getCoordinates().getLat());
        docLocation.put("longitude",location.getCoordinates().getLon());
        docLocation.put("alias",location.getAlias());
        docLocation.put("active",location.getIsActive());
        db.collection("users").document(email).collection("locations").document(location.getName()).set(docLocation);
        addAPIs(email, location.getName());
    }

    public List<Location> getUserLocations(String email) throws IncorrectLocationException {
        try {
            if (email == null) {
                throw new IncorrectLocationException();
            }
            try {
                getUserDocument(email);
            } catch (IncorrectUserException e) {
                return null;
            }

            List<QueryDocumentSnapshot> documents=db.collection("users").document(email).collection("locations").get().get().getDocuments();
            if (documents.isEmpty()){
                return new LinkedList<>();
            }
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

    public List<API> getUserLocationAPIs(String email, Location location) throws IncorrectLocationException {
        try {
            ApiFuture<QuerySnapshot> future=db.collection("users").document(email).collection("locations").document(location.getName()).collection("apis").get();
            List<QueryDocumentSnapshot> documents=future.get().getDocuments();
            List<API> apis=new LinkedList<>();
            for (QueryDocumentSnapshot document:documents){
                String name=(String) document.getData().get("name");
                API api=switch (name){
                    case "OpenWeather" -> new OpenWeather();
                    case "TicketMaster" -> new TicketMaster();
                    case "NewsAPI" -> new NewsAPI();
                    default -> null;
                };
                if (api!=null){
                    api.setName(name);
                    api.setActive((Boolean) document.getData().get("active"));
                    apis.add(api);
                }
            }
            return apis;
        } catch (InterruptedException | ExecutionException e) {
            throw new IncorrectLocationException();
        }
    }

    public API getUserLocationAPI(String email, String locationName, String apiName){
        API api=switch (apiName){
            case "OpenWeather" -> new OpenWeather();
            case "TicketMaster" -> new TicketMaster();
            case "NewsAPI" -> new NewsAPI();
            default -> null;
        };
        if (api!=null){
            api.setName(apiName);
            api.setActive((Boolean) getUserLocationAPIDocument(email,locationName,apiName).getData().get("active"));
        }
        return api;
    }

    private QueryDocumentSnapshot getUserDocument(String email) throws IncorrectUserException {
        if (email == null) {
            throw new IncorrectUserException();
        }
        try {
            List<QueryDocumentSnapshot> documents = db.collection("users").get().get().getDocuments();
            for (QueryDocumentSnapshot document : documents) {

                if (document.getData().get("email").equals(email)) {
                    return document;
                }
            }
            return null;
        } catch (InterruptedException | ExecutionException e) {
            throw new IncorrectUserException();
        }
    }

    private QueryDocumentSnapshot getLocationDocument(String email, String locationName) {
        if (locationName==null || email==null){
            return null;
        }
        QueryDocumentSnapshot docEmail;
        try {
            docEmail = getUserDocument(email);
        } catch (IncorrectUserException e) {
            throw new RuntimeException(e);
        }
        ApiFuture<QuerySnapshot> future=docEmail.getReference().collection("locations").whereEqualTo("name",locationName).get();
        try {
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            if (documents.size() == 0) {
                return null;
            }
            return documents.get(0);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public Location getUserLocation(String email, String locationName) throws ExecutionException, InterruptedException {
        System.out.println(email+"  "+locationName);
        if (locationName==null || email==null){
            return null;
        }
        UserFacade user=getUser(email);
        System.out.println("holi "+user.getEmail());
        List<QueryDocumentSnapshot> documents=db.collection("users").document(email).collection("locations").get().get().getDocuments();
        QueryDocumentSnapshot locDoc=null;
        for (QueryDocumentSnapshot document: documents){
            System.out.println("AAAA"+document.getData());
            if (document.getData().get("name").toString().equals(locationName)){
                locDoc=document;
                break;
            }
        }
        if (locDoc==null){
            return null;
        }
        Location location=new Location();
        location.setName((String) locDoc.getData().get("name"));
        Coordinates coordinates=new Coordinates();
        coordinates.setLat((Double) locDoc.getData().get("latitude"));
        coordinates.setLon((Double) locDoc.getData().get("longitude"));
        location.setCoordinates(coordinates);
        location.setActive((Boolean) locDoc.getData().get("active"));
        return location;
    }

    public void deleteUserLocations(String email) throws IncorrectUserException {
        if (email == null) {
            throw new IncorrectUserException();
        }
        QueryDocumentSnapshot docEmail = getUserDocument(email);
        if (docEmail == null) {
            throw new IncorrectUserException();
        }
        List<QueryDocumentSnapshot> documents;
        try {
            documents = docEmail.getReference().collection("locations").get().get().getDocuments();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        for (QueryDocumentSnapshot document : documents) {
            deleteUserLocationAPIs(document);
            document.getReference().delete();
        }
    }

    private void deleteUserLocationAPIs(QueryDocumentSnapshot document){
        List<QueryDocumentSnapshot> documents;
        try {
            documents = document.getReference().collection("apis").get().get().getDocuments();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        for (QueryDocumentSnapshot doc : documents) {
            doc.getReference().delete();
        }
    }

    public void changeUserLocationStatus(String email, Location location) throws NotSavedException {
        if (email == null || location == null) {
            throw new NotSavedException();
        }
        db.collection("users").document(email).collection("locations").document(location.getName()).update("active", !location.getIsActive());
    }

    public API getAPI(String email, String name) {
        if (name==null){
            return null;
        }
        QueryDocumentSnapshot document;
        document = getAPIDocument(email, name);
        if (document==null){
            return null;
        }
        String nameAPI=(String) document.getData().get("name");
        API api = switch (nameAPI) {
            case "OpenWeather" -> new OpenWeather();
            case "NewsAPI" -> new NewsAPI();
            case "TicketMaster" -> new TicketMaster();
            default -> null;
        };
        if (api==null){
            return null;
        }
        api.setName((String) document.getData().get("name"));
        api.setActive((Boolean) document.getData().get("isActive"));
        return api;
    }

    private QueryDocumentSnapshot getAPIDocument(String email, String name) {
        if (name==null){
            return null;
        }
        ApiFuture<QuerySnapshot> future;
        try {
            future = getUserDocument(email).getReference().collection("apis").get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                if (document.getData().get("name").equals(name)) {
                    return document;
                }
            }
        } catch (InterruptedException | ExecutionException | IncorrectUserException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private QueryDocumentSnapshot getUserLocationAPIDocument(String email,String locationName, String name) {
        if (email==null || locationName==null || name==null){
            return null;
        }
        QueryDocumentSnapshot docLocation=getLocationDocument(email,locationName);
        ApiFuture<QuerySnapshot> future;
        try {
            future = docLocation.getReference().collection("apis").get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                if (document.getData().get("name").equals(name)) {
                    return document;
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void changeAPIStatus(String email, API api) throws NotSavedException {
        if (email == null || api == null) {
            throw new NotSavedException();
        }
        QueryDocumentSnapshot document;
        document = getAPIDocument(email,api.getName());
        if (document==null){
            throw new NotSavedException();
        }
        document.getReference().update("isActive",!api.getIsActive());
    }

    public void changeUserLocationAPIStatus(String email, String locationName, API api) throws NotSavedException {
        if (email == null || api == null || locationName==null) {
            throw new NotSavedException();
        }
        QueryDocumentSnapshot document;
        document = getUserLocationAPIDocument(email,locationName,api.getName());
        if (document==null){
            throw new NotSavedException();
        }
        document.getReference().update("active",!api.getIsActive());
    }

    public void deleteUserAPIs(String email) throws IncorrectUserException {
        if (email == null) {
            throw new IncorrectUserException();
        }
        try {
            ApiFuture<QuerySnapshot> future = getUserDocument(email).getReference().collection("apis").get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                document.getReference().delete();
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public List<API> getUserAPIS(String email) throws NotExistingAPIException {
        try {
            if (email == null) {
                throw new NotExistingAPIException();
            }
            if (getUser(email) == null) {
                return null;
            }
            ApiFuture<QuerySnapshot> future = getUserDocument(email).getReference().collection("apis").get();
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
                api.setActive((Boolean) document.getData().get("active"));
                apis.add(api);
            }
            return apis;
        } catch (InterruptedException | ExecutionException | IncorrectUserException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteUserLocation(String email, String locationName) throws IncorrectLocationException{
        QueryDocumentSnapshot document;
        document = getLocationDocument(email, locationName);
        if (document==null){
            throw new IncorrectLocationException();
        }
        deleteUserLocationAPIs(document);
        document.getReference().delete();
    }
}
