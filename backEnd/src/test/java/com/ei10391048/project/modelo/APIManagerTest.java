package com.ei10391048.project.modelo;

import com.ei10391048.project.exception.IncorrectLocationException;
import com.ei10391048.project.exception.NonActiveServiceException;
import com.ei10391048.project.modelo.api.OpenWeather;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.testng.Assert.*;

public class APIManagerTest {

    List<Location> locationList = new LinkedList<>();
    @BeforeEach
    public void setParams() throws IncorrectLocationException {
        LocationManager locationManager = LocationManager.getInstance();
        GeoCodService geoCodSrv = new GeoCodService();
        String toponimo = "Valencia";
        geoCodSrv.setSearch(new ByName(toponimo));
        locationManager.setLocationApi(geoCodSrv);

        locationManager.addLocation();

        toponimo = "Madrid";
        geoCodSrv.setSearch(new ByName(toponimo));

        locationManager.addLocation();

        toponimo = "Castellón";
        geoCodSrv.setSearch(new ByName(toponimo));

        locationManager.addLocation();

    }
    @Test
    public void getInfoFrom1LocationValidTest() {
        int index = 0;
        LocationManager locationManager = LocationManager.getInstance();
        Location location = locationManager.getLocations().get(index);
        APIManager manager = location.getApiManager();
        manager.addAPI(new OpenWeather());
        assertEquals(location.getInfo().get(0).getLocationName(), locationManager.getLocations().get(index).getName());

    }

    @Test
    public void getInfoFrom1LocationInvalidTest(){

        LocationManager locationManager = LocationManager.getInstance();
        APIManager manager = locationManager.getLocations().get(0).getApiManager();

        try {
            manager.getAPI(APIsNames.EVENTS);

            fail();
        } catch (NonActiveServiceException ex){
            assertTrue(true);
        }

    }
}
