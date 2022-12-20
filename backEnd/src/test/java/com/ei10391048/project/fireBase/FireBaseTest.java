package com.ei10391048.project.fireBase;

import com.ei10391048.project.exception.NotSavedException;
import com.ei10391048.project.modelo.Location;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.fail;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;
import java.util.stream.Stream;

public class FireBaseTest {
    private static CRUDFireBase crudFireBase;
    private static Location location;
    @BeforeAll
    public static void setUp() throws ExecutionException, InterruptedException {
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
            crudFireBase.getLocation(location);
            assertTrue(true);
        } catch (ExecutionException | InterruptedException exception){
            fail();
        }
    }

    @ParameterizedTest
    @MethodSource("locations")
    public void getLocationFromBBDDInvalid(Location location) throws ExecutionException, InterruptedException {
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

    @AfterAll
    public static void tearDown() throws ExecutionException, InterruptedException {
        crudFireBase.deleteLocations();
    }
}
