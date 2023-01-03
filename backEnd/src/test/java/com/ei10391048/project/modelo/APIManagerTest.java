package com.ei10391048.project.modelo;

import com.ei10391048.project.exception.IncorrectLocationException;
import com.ei10391048.project.exception.IncorrectUserException;
import com.ei10391048.project.exception.NotSavedException;
import com.ei10391048.project.modelo.api.APIsNames;
import com.ei10391048.project.modelo.information.APIInformation;
import com.ei10391048.project.modelo.user.User;
import com.ei10391048.project.modelo.user.UserManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class APIManagerTest {


    static LocationManager manager;
    static InformationLocationManager informationLocationManager;
    static User user = new User();
    @BeforeAll
    public static void setParams() throws IncorrectLocationException, NotSavedException, IncorrectUserException {
        user = new User();
        user.setEmail("test@gmail.com");
        UserManager.getInstance().getUserList().add(user);
        user = UserManager.getInstance().getUser("test@gmail.com");
        manager = user.getLocationManager();

        informationLocationManager = user.getInformationLocationManager();

        String toponimo = "Valencia";
        Location location = new Location(toponimo, 39.46975, -0.37739);
        manager.addUserLocation(location);

        toponimo = "Madrid";
        location = new Location(toponimo, 40.416775, -3.70379);
        manager.addUserLocation(location);

        toponimo = "Castellón";
        location = new Location(toponimo, 39.986212, -0.037049);
        manager.addUserLocation(location);


    }
    @AfterAll
    static void delete(){
        UserManager.getInstance().deleteAllUsers();
    }
    /**
     * Test que comprueba la historia de usuario 15:  Como usuario quiero consultar
     * información de múltiples ubicaciones simultáneamente con el fin de estar al
     * corriente de novedades en todas ellas.
     *
     */

    @Test
    public void getInfoFromAllLocationsValidTest(){
        List<List<List<APIInformation>>> list = informationLocationManager.getAllActivatedInfo(user);
        assertEquals(list.size(),manager.getActiveLocations().size());

        for (int i=0;i<list.size();i++){
            Location location = manager.getActiveLocations().get(i);
            assertEquals(location.getApiManager().getInformation(APIsNames.WEATHER.getOrder()), list.get(i).get(APIsNames.WEATHER.getOrder()));
            assertEquals(location.getApiManager().getInformation(APIsNames.NEWS.getOrder()), list.get(i).get(APIsNames.NEWS.getOrder()));
            assertEquals(location.getApiManager().getInformation(APIsNames.EVENTS.getOrder()), list.get(i).get(APIsNames.EVENTS.getOrder()));

        }


    }

    /**
     * Test que comprueba la historia de usuario 18:  Como usuario quiero consultar
     * fácilmente la información de cualquiera de las ubicaciones activas por separado.
     *
     */
    @Test
    public void getInfoFrom1LocationValidTest() {
        int index = 0;
        Location location = manager.getUserLocations().get(index);
        ApiFacade apiManager = location.getApiManager();
        apiManager.generateInfo(location.getName());
        assertEquals(apiManager.getInformation(APIsNames.WEATHER.getOrder()).get(0).getLocationName(), manager.getUserLocations().get(index).getName());

    }

}
