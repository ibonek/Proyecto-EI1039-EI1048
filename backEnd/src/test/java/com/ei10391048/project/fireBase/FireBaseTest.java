package com.ei10391048.project.fireBase;

import com.ei10391048.project.exception.IncorrectLocationException;
import com.ei10391048.project.exception.NotSavedException;
import com.ei10391048.project.modelo.Location;
import com.ei10391048.project.modelo.api.API;
import com.ei10391048.project.modelo.api.OpenWeather;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

public class FireBaseTest {
    private static CRUDFireBase crudFireBase;
    @BeforeEach
    public void setUp() {
        crudFireBase = new CRUDFireBase();
        crudFireBase.deleteLocations();
        crudFireBase.deleteAPIs();
    }
    @Test
    public void addLocationToBBDDValid(){
        Location location = new Location("Teruel", 40.345, -0.6667);
        try {
            crudFireBase.addLocation(location);
            assertTrue(true);
        } catch (NotSavedException exception){
            fail();
        }
    }

    @Test
    public void addLocationToBBDDInvalid(){
        try {
            crudFireBase.addLocation(null);
            fail();
        } catch (NotSavedException exception){
            assertTrue(true);
        }
    }

    @Test
    public void getLocationFromBBDDValid(){
        Location location = new Location("Teruel", 40.345, -0.6667);
        try {
            crudFireBase.addLocation(location);
            Location location2 = crudFireBase.getLocation(location);
            assertEquals(location, location2);
        } catch (NotSavedException e) {
            fail();
        }
    }

    @ParameterizedTest
    @MethodSource("locations")
    public void getLocationFromBBDDInvalid(Location location) {
        assertNull(crudFireBase.getLocation(location));
    }

    static Stream<Arguments> locations(){
        return Stream.of(
                Arguments.of(new Location("Teruel", 40.345, -0.6687)),
                Arguments.of(new Location("Valencia", 39.4697600, -0.3773900)),
                Arguments.of(new Location("Madridd", 40.4167754, -3.7037902)),
                Arguments.of((Object) null)
        );
    }

    @Test
    public void deleteLocationsFromBBDDValid(){
        Location location = new Location("Teruel", 40.345, -0.6667);
        try {
            crudFireBase.addLocation(location);
            crudFireBase.deleteLocations();
            assertEquals(0, crudFireBase.getLocations().size());
        } catch (NotSavedException | IncorrectLocationException e){
            fail();
        }
    }

    @Test
    public void deleteLocationsFromBBDDInvalid(){
        try {
            crudFireBase.deleteLocations();
            System.out.println(crudFireBase.getLocations());
            assertEquals(0, crudFireBase.getLocations().size());
        } catch (IncorrectLocationException e) {
            fail();
        }
    }

    @Test
    public void deleteLocationValid() {
        Location location = new Location("Teruel", 40.345, -0.6667);
        try {
            crudFireBase.addLocation(location);
            int size = crudFireBase.getLocations().size();
            crudFireBase.deleteLocation(location);
            assertEquals(size-1, crudFireBase.getLocations().size());
        } catch (NotSavedException | IncorrectLocationException e) {
            fail();
        }
    }

    @ParameterizedTest
    @MethodSource("deleteLocations")
    public void deleteLocationInvalid(Location location) {
        try {
            crudFireBase.deleteLocation(location);
            fail();
        } catch (IncorrectLocationException e) {
            assertTrue(true);
        }
    }

    public static Stream<Arguments> deleteLocations() {
        return Stream.of(
                Arguments.of(new Location("Teruel", 40.345, -0.6667)),
                Arguments.of((Object) null)
        );
    }

    @Test
    public void getLocationsFromBBDDValid(){
        Location location = new Location("Teruel", 40.345, -0.6667);
        try {
            crudFireBase.addLocation(location);
            assertEquals(1, crudFireBase.getLocations().size());
        } catch (NotSavedException | IncorrectLocationException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void activateLocationFromBBDDValid(){
        Location location = new Location("Teruel", 40.345, -0.6667);
        try {
            location.setActive(false);
            crudFireBase.addLocation(location);
            crudFireBase.changeLocationStatus(location);
            assertTrue(crudFireBase.getLocation(location).getIsActive());
        } catch (NotSavedException exception) {
            fail();
        }
    }

    @ParameterizedTest
    @MethodSource("status")
    public void activateLocationFromBBDDInvalid(Location status) {
        try {
            crudFireBase.changeLocationStatus(status);
            fail();
        } catch (NotSavedException e) {
            assertTrue(true);
        }
    }

    static Stream<Arguments> status(){
        return Stream.of(
                Arguments.of((Object) null),
                Arguments.of(new Location("Teruel", 40.345, -0.6667))
        );
    }

    @Test
    public void deactivateLocationFromBBDDValid(){
        Location location = new Location("Teruel", 40.345, -0.6667);
        try {
            crudFireBase.addLocation(location);
            crudFireBase.changeLocationStatus(location);

            assertFalse(crudFireBase.getLocation(location).getIsActive());
        } catch (NotSavedException exception) {
            fail();
        }
    }

    @ParameterizedTest
    @MethodSource("status")
    public void deactivateLocationFromBBDDInvalid(Location status) {
        try {
            crudFireBase.changeLocationStatus(status);
            fail();
        } catch (NotSavedException e) {
            assertTrue(true);
        }
    }

    @Test
    public void addAPIValid(){
        API api= new OpenWeather();
        try {
            crudFireBase.addAPI(api);
            assertTrue(true);
        } catch (NotSavedException exception){
            fail();
        }
    }

    @Test
    public void addAPIInvalid(){
        try {
            crudFireBase.addAPI(null);
            fail();
        } catch (NotSavedException exception){
            assertTrue(true);
        }
    }

    @Test
    public void activateAPIValid(){
        API api= new OpenWeather();
        try {
            api.setActive(false);
            crudFireBase.addAPI(api);
            crudFireBase.changeAPIStatus(api);
            assertTrue(crudFireBase.getAPI(api).getIsActive());
        } catch (NotSavedException exception) {
            fail();
        }
    }

    @Test
    public void activateAPIInvalid() {
        try {
            crudFireBase.changeAPIStatus(null);
            fail();
        } catch (NotSavedException e) {
            assertTrue(true);
        }
    }
/*
    @AfterAll
    public static void tearDown() throws ExecutionException, InterruptedException {
        crudFireBase.deleteLocations();
    }*/
}
