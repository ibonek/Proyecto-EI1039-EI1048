package com.ei10391048.project.modelo;

import com.ei10391048.project.exception.IncorrectLocationException;
import com.ei10391048.project.exception.IncorrectUserException;
import com.ei10391048.project.exception.NotSavedException;
import com.ei10391048.project.modelo.api.APIsNames;
import com.ei10391048.project.modelo.information.APIInformation;
import com.ei10391048.project.modelo.user.User;
import com.ei10391048.project.modelo.user.UserManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class APIManagerTest {


    UserManager userManager = UserManager.getInstance();
    LocationManager manager;
    InformationLocationManager informationLocationManager;
    User user = new User("test@gmail.com");
    @BeforeEach
    public void setParams() throws IncorrectLocationException, NotSavedException, IncorrectUserException {
        manager = user.getLocationManager();

        manager.clearLocations();;
        informationLocationManager = user.getInformationLocationManager();

        String toponimo = "Valencia";
        user.addUserLocation(toponimo);

        toponimo = "Madrid";
        user.addUserLocation(toponimo);

        toponimo = "Castellón";

        user.addUserLocation(toponimo);


    }
    /**
     * Test que comprueba la historia de usuario 15:  Como usuario quiero consultar información de múltiples ubicaciones simultáneamente
     * con el fin de estar al corriente de novedades en todas ellas.
     *
     */

    @Test
    public void getInfoFromAllLocationsValidTest(){
        List<List<List<APIInformation>>> list = user.getAllActivatedInfo();
        assertEquals(list.size(),manager.getActiveLocations().size());

        for (int i=0;i<list.size();i++){
            Location location = manager.getActiveLocations().get(i);
            assertEquals(location.getApiManager().getInformation(APIsNames.WEATHER.getOrder()), list.get(i).get(APIsNames.WEATHER.getOrder()));
            assertEquals(location.getApiManager().getInformation(APIsNames.NEWS.getOrder()), list.get(i).get(APIsNames.NEWS.getOrder()));
            assertEquals(location.getApiManager().getInformation(APIsNames.EVENTS.getOrder()), list.get(i).get(APIsNames.EVENTS.getOrder()));

        }


    }



    /**
     * Test que comprueba la historia de usuario 18:  Como usuario quiero consultar fácilmente la información de cualquiera de las ubicaciones activas por separado.
     *
     */
    @Test
    public void getInfoFrom1LocationValidTest() {
        int index = 0;
        Location location = manager.getLocations().get(index);
        ApiFacade apiManager = location.getApiManager();
        apiManager.generateInfo(location.getName());
        assertEquals(apiManager.getInformation(APIsNames.WEATHER.getOrder()).get(0).getLocationName(), manager.getLocations().get(index).getName());

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
