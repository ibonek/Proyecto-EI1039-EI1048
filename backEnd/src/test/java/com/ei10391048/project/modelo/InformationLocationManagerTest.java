package com.ei10391048.project.modelo;

import com.ei10391048.project.exception.IncorrectLocationException;
import com.ei10391048.project.exception.NotExistingAPIException;
import com.ei10391048.project.exception.NotSavedException;
import com.ei10391048.project.modelo.api.API;
import com.ei10391048.project.modelo.api.APIsNames;
import com.ei10391048.project.modelo.user.User;
import com.ei10391048.project.modelo.user.UserFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InformationLocationManagerTest {

    LocationManager manager;
    InformationLocationManager informationLocationManager;

    UserFacade user = new User();
    @BeforeEach
    void setUp() throws NotSavedException, IncorrectLocationException {
        user=new User();
        user.setEmail("test@gmail.com");
        manager = user.getLocationManager();
        informationLocationManager = user.getInformationLocationManager();
        user.addLocation("Castellón");
        user.addLocation("Madrid");
    }

    @Test
    void getApiList(){
        List<API> apilist = informationLocationManager.getApis();
        List<String> list = new LinkedList<>();
        for(APIsNames api: APIsNames.values()){
            list.add(api.getOrder(),api.getApiName());
        }

        assertEquals(apilist.size(), list.size());

        for (int i = 0; i<apilist.size(); i++){
            assertEquals(apilist.get(i).getAPIName(), list.get(i));
        }
    }

    /**
     Test que comprueba la historia de usuario 20: Como usuario quiero activar un servicio de información (API), entre aquellos disponibles
     */
    @Test
    public void activateAPIValidCase() throws NotExistingAPIException {
        List<API> apiList = informationLocationManager.getApis();
        for (int i=0;i<apiList.size();i++){
            assertTrue(apiList.get(i).getIsActive());
            user.changeAPIState(i);
            user.changeAPIState(i);
            assertTrue(apiList.get(i).getIsActive());
        }
    }
    @Test
    public void activateAPIInvalidCase() throws NotExistingAPIException {
        for(int i=0;i<informationLocationManager.getApis().size();i++){
            user.changeAPIState(i);
        }
        try{
            user.changeAPIState(-1);
            fail();
        }catch(NotExistingAPIException ex){
            for(int i=0;i<informationLocationManager.getApis().size();i++){
                assertFalse(informationLocationManager.getApis().get(i).getIsActive());
                user.changeAPIState(i);            }
        }
    }

    @Test
    public void changeApiStateTest() throws NotExistingAPIException {
        List<API> apiList = informationLocationManager.getApis();
        for (int i = 0; i<apiList.size();i++){
            API api = apiList.get(i);
            Location location = manager.getActiveLocations().get(0);
            assertEquals(api.getIsActive(), location.getApiManager().getApiList().get(i).getIsActive());
            user.changeAPIState(i);
            assertEquals(api.getIsActive(), location.getApiManager().getApiList().get(i).getIsActive());
            user.changeAPIState(i);
        }
    }
    /**
     * Test que comprueba la historia de usuario 22:  Como usuario quiero desactivar un servicio de información que haya dejado de interesar,
     * con el fin de evitar interfaces de usuario sobrecargadas.
     *
     */

    @Test
    public void deactivateAPIValidCase() throws NotExistingAPIException {
        List<API> apiList = informationLocationManager.getApis();
        for (int i=0;i<apiList.size();i++){
            user.changeAPIState(i);
            assertFalse(apiList.get(i).getIsActive());
            user.changeAPIState(i);        }
    }
    @Test
    public void deactivateAPIInvalidCase(){
        try{
            user.changeAPIState(informationLocationManager.getApis().size());
            fail();
        }catch(NotExistingAPIException ex){
            for(API api:informationLocationManager.getApis()){
                assertTrue(api.getIsActive());
            }
        }
    }

    @Test
    void activateLocationValidCase() {
        Location location = user.getMyLocations().get(0);
        location.setActive(!location.getIsActive());
        location.setActive(!location.getIsActive());
        assertTrue(user.getMyLocations().get(0).getIsActive());
    }

}
