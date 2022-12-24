package com.ei10391048.project.fireBase;

import com.ei10391048.project.exception.IncorrectLocationException;
import com.ei10391048.project.exception.NotSavedException;
import com.ei10391048.project.modelo.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

public class FireBaseTest {
    private static CRUDFireBase crudFireBase;
    @BeforeEach
    public void setUp() throws ExecutionException, InterruptedException {
        crudFireBase = new CRUDFireBase();
        crudFireBase.deleteLocations();
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
            throw new RuntimeException(e);
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
        } catch (ExecutionException | InterruptedException |NotSavedException | IncorrectLocationException e){
            fail();
        }
    }

    @Test
    public void deleteLocationsFromBBDDInvalid(){
        try {
            crudFireBase.deleteLocations();
            assertEquals(0, crudFireBase.getLocations().size());
        } catch (ExecutionException | InterruptedException exception){
            fail();
        } catch (IncorrectLocationException e) {
            throw new RuntimeException(e);
        }
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
    public void getLocationsFromBBDDInvalid(){
        try {
            assertEquals(0, crudFireBase.getLocations().size());
        } catch (IncorrectLocationException e) {
            fail();
        }
    }

    @Test
    public void activateLocationFromBBDDValid(){
        Location location = new Location("Teruel", 40.345, -0.6667);
        try {
            location.setActive(false);
            crudFireBase.addLocation(location);
            crudFireBase.changeStatus(location);
            assertTrue(crudFireBase.getLocation(location).getIsActive());
        } catch (NotSavedException exception) {
            fail();
        }
    }

    @ParameterizedTest
    @MethodSource("status")
    public void activateLocationFromBBDDInvalid(Location status) {
        try {
            crudFireBase.changeStatus(status);
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
            crudFireBase.changeStatus(location);
            assertFalse(crudFireBase.getLocation(location).getIsActive());
        } catch (NotSavedException exception) {
            fail();
        }
    }

    @ParameterizedTest
    @MethodSource("status")
    public void deactivateLocationFromBBDDInvalid(Location status) {
        try {
            crudFireBase.changeStatus(status);
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
