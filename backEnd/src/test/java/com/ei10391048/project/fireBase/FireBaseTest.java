package com.ei10391048.project.fireBase;

import com.ei10391048.project.exception.NotSavedException;
import com.ei10391048.project.modelo.Location;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;
import static org.testng.Assert.assertTrue;

public class FireBaseTest {
    private static CRUDFireBase crudFireBase;
    private static Location location;
    @BeforeAll
    public static void setUp() {
        location = new Location("Teruel", 40.345, -0.6667);
        crudFireBase = new CRUDFireBase();
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
}
