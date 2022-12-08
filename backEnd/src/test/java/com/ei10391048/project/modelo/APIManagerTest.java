package com.ei10391048.project.modelo;

import com.ei10391048.project.exception.IncorrectLocationException;
import com.ei10391048.project.exception.NonActiveServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class APIManagerTest {

    List<Location> locationList = new LinkedList<>();
    @BeforeEach
    public void setParams() throws IncorrectLocationException {
        GeoCodService geoCodSrv = new GeoCodService();
        String toponimo = "Valencia";
        geoCodSrv.setSearch(new ByName(toponimo));

        locationList.add(geoCodSrv.findLocation());

        toponimo = "Madrid";
        geoCodSrv.setSearch(new ByName(toponimo));

        locationList.add(geoCodSrv.findLocation());

        toponimo = "Castellón";
        geoCodSrv.setSearch(new ByName(toponimo));

        locationList.add(geoCodSrv.findLocation());

    }
    @Test
    public void getInfoFrom1LocationValidTest() throws NonActiveServiceException {
        int index = 0;
        LocationManager locationManager = LocationManager.getInstance();
        APIManager apiManager = new APIManager();
        apiManager.addAPI(new OpenWeather());
        locationManager.setApiManager(apiManager);
        API api = locationManager.getApiManager().getAPI(APIsNames.WEATHER);

        assertEquals(api.getInfo().getName(), locationManager.getLocations().get(index).getName());

    }

    @Test
    public void getInfoFrom1LocationInvalidTest(){
        int index = 0;
        LocationManager locationManager = LocationManager.getInstance();
        APIManager apiManager = new APIManager();
        locationManager.setApiManager(apiManager);
        try {
            API api = locationManager.getApiManager().getAPI(APIsNames.WEATHER);

            assertTrue(false);
        } catch (NonActiveServiceException ex){
            assertTrue(true);
        }

    }
}
