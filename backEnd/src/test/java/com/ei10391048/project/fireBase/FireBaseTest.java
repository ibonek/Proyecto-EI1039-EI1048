package com.ei10391048.project.fireBase;

import com.ei10391048.project.exception.AlreadyActiveLocation;
import com.ei10391048.project.exception.NotSavedException;
import com.ei10391048.project.modelo.Location;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.testng.Assert;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;
import java.util.stream.Stream;

public class FireBaseTest {
    private static CRUDFireBase crudFireBase;
    private static Location location;
    @BeforeEach
    public void setUp() throws ExecutionException, InterruptedException {
        location = new Location("Teruel", 40.345, -0.6667);
        crudFireBase = new CRUDFireBase();
        crudFireBase.deleteLocations();
    }
    @Test
    public void addLocationToBBDDValid(){
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
        try {
            crudFireBase.addLocation(location);
            Location location2 = crudFireBase.getLocation(location);
            Assert.assertEquals(location, location2);
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
        try {
            crudFireBase.addLocation(location);
            crudFireBase.deleteLocations();
            assertEquals(0, crudFireBase.getLocations().size());
        } catch (ExecutionException | InterruptedException exception){
            fail();
        } catch (NotSavedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void deleteLocationsFromBBDDInvalid(){
        try {
            crudFireBase.deleteLocations();
            assertEquals(0, crudFireBase.getLocations().size());
        } catch (ExecutionException | InterruptedException exception){
            fail();
        }
    }

    @Test
    public void getLocationsFromBBDDValid(){
        try {
            crudFireBase.addLocation(location);
            assertEquals(1, crudFireBase.getLocations().size());
        } catch (NotSavedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getLocationsFromBBDDInvalid(){
        assertEquals(0, crudFireBase.getLocations().size());
    }

    @Test
    public void activateLocationFromBBDDValid(){
        try {
            location.setActive(false);
            crudFireBase.addLocation(location);
            crudFireBase.activateLocation(location);
            assertTrue(crudFireBase.getLocation(location).getIsActive());
        } catch (NotSavedException | AlreadyActiveLocation exception) {
            fail();
        }
    }

    @Test
    public void activateLocationFromBBDDInvalid() {
        try {
            crudFireBase.activateLocation(null);
            fail();
        } catch (NotSavedException e) {
            assertTrue(true);
        } catch (AlreadyActiveLocation e) {
            fail();
        }
    }

    @Test
    public void activateLocationFromBBDDInvalid2() {
        location.setActive(true);
        try {
            crudFireBase.activateLocation(location);
            fail();
        } catch (NotSavedException e) {
            fail();
        } catch (AlreadyActiveLocation e) {
            assertTrue(true);
        }
    }
    @AfterAll
    public static void tearDown() throws ExecutionException, InterruptedException {
        crudFireBase.deleteLocations();
    }
}
