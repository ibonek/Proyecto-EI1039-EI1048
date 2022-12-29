package com.ei10391048.project.fireBase;

import com.ei10391048.project.exception.IncorrectLocationException;
import com.ei10391048.project.exception.IncorrectUserException;
import com.ei10391048.project.exception.NotSavedException;
import com.ei10391048.project.modelo.Location;
import com.ei10391048.project.modelo.api.API;
import com.ei10391048.project.modelo.api.OpenWeather;
import com.ei10391048.project.modelo.user.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

public class FireBaseTest {

    private static CRUDFireBase crudFireBase;
    private static User user;
    /*@BeforeAll
    public static void signUpValid() {
        user = new User();
        user.setEmail("al394885@uji.es");
        user.setPassword("123456");
        crudFireBase = CRUDFireBase.getInstance();
        try {
            crudFireBase.signUp(user.getEmail(), user.getPassword());
            assertTrue(true);
        } catch (IncorrectUserException e) {
            fail();
        }
    }*/
    @BeforeEach
    public void setUp() throws IncorrectUserException {
        user = new User();
        user.setEmail("al394885@uji.es");
        user.setPassword("123456");
        crudFireBase = CRUDFireBase.getInstance();
        //crudFireBase.deleteUserLocations(user.getEmail());
        //crudFireBase.deleteAPIs();
    }

    @Test
    public void addUser() {
        try {
            crudFireBase.addUser(user.getEmail());
            assertEquals(user.getEmail(), crudFireBase.getUser(user.getEmail()).getEmail());
        } catch (IncorrectUserException e) {
            fail();
        }
    }
    @Test
    public void addUserLocationToBBDDValid(){
        Location location = new Location("Teruel", 40.345, -0.6667);
        try {
            crudFireBase.addLocation(location,user.getEmail());
            assertTrue(true);
        } catch (NotSavedException exception){
            fail();
        }
    }

    @Test
    public void addUserLocationToBBDDInvalid(){
        try {
            crudFireBase.addLocation(null, user.getEmail());
            fail();
        } catch (NotSavedException exception){
            assertTrue(true);
        }
    }

    @Test
    public void getUserLocationFromBBDDValid(){
        Location location = new Location("Teruel", 40.345, -0.6667);
        try {
            crudFireBase.addLocation(location, user.getEmail());
            assertEquals(location, crudFireBase.getUserLocation(user.getEmail(), location.getName()));
        } catch (NotSavedException e) {
            fail();
        }
    }

    @ParameterizedTest
    @MethodSource("locations")
    public void getUserLocationFromBBDDInvalid(String locationName){
        assertNull(crudFireBase.getUserLocation(user.getEmail(), locationName));
    }

    static Stream<Arguments> locations(){
        return Stream.of(
                Arguments.of("Valencia"),
                Arguments.of((Object) null)
        );
    }

    @Test
    public void deleteUserLocationsFromBBDDValid(){
        Location location = new Location("Teruel", 40.345, -0.6667);
        try {
            crudFireBase.addLocation(location, user.getEmail());
            crudFireBase.deleteUserLocations(user.getEmail());
            assertEquals(0, crudFireBase.getUserLocations(user.getEmail()).size());
        } catch (NotSavedException | IncorrectLocationException e){
            fail();
        } catch (IncorrectUserException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void deleteLocationsFromBBDDInvalid(){
        try {
            crudFireBase.deleteUserLocations(user.getEmail());
            assertEquals(0, crudFireBase.getUserLocations(user.getEmail()).size());
        } catch (IncorrectLocationException e) {
            fail();
        } catch (IncorrectUserException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void deleteLocationValid() {
        Location location = new Location("Teruel", 40.345, -0.6667);
        try {
            crudFireBase.addLocation(location, user.getEmail());
            int size = crudFireBase.getUserLocations(user.getEmail()).size();
            crudFireBase.deleteUserLocation(user.getEmail(), location.getName());
            assertEquals(size-1, crudFireBase.getUserLocations(user.getEmail()).size());
        } catch (NotSavedException | IncorrectLocationException e) {
            fail();
        }
    }

    @ParameterizedTest
    @MethodSource("deleteLocations")
    public void deleteUserLocationInvalid(String locationName){
        try {
            crudFireBase.deleteUserLocation(user.getEmail(), locationName);
            fail();
        } catch (IncorrectLocationException e) {
            assertTrue(true);
        }
    }

    public static Stream<Arguments> deleteLocations() {
        return Stream.of(
                Arguments.of("Teruel"),
                Arguments.of((Object) null)
        );
    }

    @Test
    public void getUserLocationsFromBBDDValid(){
        Location location = new Location("Teruel", 40.345, -0.6667);
        try {
            crudFireBase.addLocation(location, user.getEmail());
            assertEquals(1, crudFireBase.getUserLocations(user.getEmail()).size());
        } catch (NotSavedException | IncorrectLocationException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void activateUserLocationFromBBDDValid(){
        Location location = new Location("Teruel", 40.345, -0.6667);
        try {
            location.setActive(false);
            crudFireBase.addLocation(location, user.getEmail());
            crudFireBase.changeUserLocationStatus(user.getEmail(), location);
            assertTrue(crudFireBase.getUserLocation(user.getEmail(),location.getName()).getIsActive());
        } catch (NotSavedException exception) {
            fail();
        }
    }

    @ParameterizedTest
    @MethodSource("status")
    public void activateUserLocationFromBBDDInvalid(Location status) {
        try {
            crudFireBase.changeUserLocationStatus(user.getEmail(), status);
            fail();
        } catch (NotSavedException e) {
            assertTrue(true);
        }
    }

    static Stream<Arguments> status(){
        return Stream.of(
                Arguments.of((Object) null),
                Arguments.of(new Location("Madrid", 40.345, -0.6667))
        );
    }

    @Test
    public void deactivateLocationFromBBDDValid(){
        Location location = new Location("Teruel", 40.345, -0.6667);
        try {
            crudFireBase.addLocation(location, user.getEmail());
            crudFireBase.changeUserLocationStatus(user.getEmail(), location);
            assertFalse(crudFireBase.getUserLocation(user.getEmail(), location.getName()).getIsActive());
        } catch (NotSavedException exception) {
            fail();
        }
    }

    @ParameterizedTest
    @MethodSource("status")
    public void deactivateLocationFromBBDDInvalid(Location status) {
        try {
            crudFireBase.changeUserLocationStatus(user.getEmail(), status);
            fail();
        } catch (NotSavedException e) {
            assertTrue(true);
        }
    }

    @Test
    public void activateAPIValid(){
        API api= new OpenWeather();
        try {
            api.setActive(false);
            crudFireBase.changeAPIStatus(user.getEmail(),api);
            assertTrue(crudFireBase.getAPI(user.getEmail(),api.getName()).getIsActive());
        } catch (NotSavedException exception) {
            fail();
        }
    }

    @Test
    public void activateAPIInvalid() {
        try {
            crudFireBase.changeAPIStatus(user.getEmail(),null);
            fail();
        } catch (NotSavedException e) {
            assertTrue(true);
        }
    }

    @Test
    public void deactivateAPIValid(){
        API api= new OpenWeather();
        try {
            crudFireBase.changeAPIStatus(user.getEmail(),api);
            assertFalse(crudFireBase.getAPI(user.getEmail(),api.getName()).getIsActive());
        } catch (NotSavedException exception) {
            fail();
        }
    }

    @Test
    public void deactivateAPIInvalid() {
        try {
            crudFireBase.changeAPIStatus(user.getEmail(),null);
            fail();
        } catch (NotSavedException e) {
            assertTrue(true);
        }
    }

    @Test
    public void activateAPILocationValid(){
        API api= new OpenWeather();
        Location location = new Location("Teruel", 40.345, -0.6667);
        try {
            crudFireBase.addLocation(location, user.getEmail());
            api.setActive(false);
            crudFireBase.changeUserLocationAPIStatus(user.getEmail(), location.getName(), api);
            assertTrue(crudFireBase.getUserLocationAPIs(user.getEmail(), location).get(0).getIsActive());
        } catch (NotSavedException exception) {
            fail();
        } catch (IncorrectLocationException e) {
            throw new RuntimeException(e);
        }
    }

    @ParameterizedTest
    @MethodSource("APILocation")
    public void activateAPILocationInvalid(API api, Location location) {
        try {
            crudFireBase.changeUserLocationAPIStatus(user.getEmail(), location.getName(), api);
            fail();
        } catch (NotSavedException e) {
            assertTrue(true);
        }
    }

    @Test
    public void deactivateAPILocationValid(){
        API api= new OpenWeather();
        Location location = new Location("Teruel", 40.345, -0.6667);
        try {
            crudFireBase.addLocation(location, user.getEmail());
            crudFireBase.changeUserLocationAPIStatus(user.getEmail(), location.getName(), api);
            assertFalse(crudFireBase.getUserLocationAPIs(user.getEmail(), location).get(0).getIsActive());
        } catch (NotSavedException exception) {
            fail();
        } catch (IncorrectLocationException e) {
            throw new RuntimeException(e);
        }
    }

    @ParameterizedTest
    @MethodSource("APILocation")
    public void deactivateAPILocationInvalid(API api, String location) {
        try {
            crudFireBase.changeUserLocationAPIStatus(user.getEmail(), location, api);
            fail();
        } catch (NotSavedException e) {
            assertTrue(true);
        }
    }

    static Stream<Arguments> APILocation(){
        return Stream.of(
                Arguments.of(null, "Teruel")
        );
    }
/*
    @AfterAll
    public static void tearDown() throws ExecutionException, InterruptedException {
        crudFireBase.deleteLocations();
    }*/
}
