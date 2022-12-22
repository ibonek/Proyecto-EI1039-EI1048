package com.ei10391048.project.modelo;

import com.ei10391048.project.exception.IncorrectLocationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.testng.Assert.*;

public class APIManagerTest {

    @BeforeEach
    public void setParams() throws IncorrectLocationException {
        LocationManager locationManager = LocationManager.getInstance();
        locationManager.clearLocations();
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
    /**
     * Test que comprueba la historia de usuario 18:  Como usuario quiero consultar fácilmente la información de cualquiera de las ubicaciones activas por separado.
     *
     */
    @Test
    public void getInfoFrom1LocationValidTest() {
        int index = 0;
        LocationManager locationManager = LocationManager.getInstance();
        Location location = locationManager.getLocations().get(index);
        ApiFacade manager = location.getApiManager();
        manager.generateInfo(location.getName());
        assertEquals(manager.getWeatherInformation().get(0).getLocationName(), locationManager.getLocations().get(index).getName());

    }

    /*
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

     */
}
