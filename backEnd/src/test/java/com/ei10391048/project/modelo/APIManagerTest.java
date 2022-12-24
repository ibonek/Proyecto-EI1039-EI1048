package com.ei10391048.project.modelo;

import com.ei10391048.project.exception.IncorrectLocationException;
import com.ei10391048.project.exception.NotSavedException;
import com.ei10391048.project.modelo.api.APIsNames;
import com.ei10391048.project.modelo.information.APIInformation;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class APIManagerTest {

    @BeforeEach
    public void setParams() throws IncorrectLocationException, NotSavedException {
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
     * Test que comprueba la historia de usuario 15:  Como usuario quiero consultar información de múltiples ubicaciones simultáneamente
     * con el fin de estar al corriente de novedades en todas ellas.
     *
     */

    @Test
    public void getInfoFromAllLocationsValidTest(){
        LocationManager manager = LocationManager.getInstance();
        List<List<List<APIInformation>>> list = manager.getAllActivatedInfo();
        assertEquals(list.size(),manager.getActiveLocations().size());

        for (int i=0;i<list.size();i++){
            Location location = manager.getActiveLocations().get(i);
            assertEquals(location.getApiManager().getWeatherInformation(), list.get(i).get(APIsNames.WEATHER.getOrder()));
            assertEquals(location.getApiManager().getNewsInformation(), list.get(i).get(APIsNames.NEWS.getOrder()));
            assertEquals(location.getApiManager().getEventsInformation(), list.get(i).get(APIsNames.EVENTS.getOrder()));

        }


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
