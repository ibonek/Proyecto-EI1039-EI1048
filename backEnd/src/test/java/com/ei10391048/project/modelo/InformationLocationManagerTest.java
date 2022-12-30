package com.ei10391048.project.modelo;

import com.ei10391048.project.exception.IncorrectLocationException;
import com.ei10391048.project.exception.IncorrectUserException;
import com.ei10391048.project.exception.NotExistingAPIException;
import com.ei10391048.project.exception.NotSavedException;
import com.ei10391048.project.modelo.api.API;
import com.ei10391048.project.modelo.api.APIsNames;
import com.ei10391048.project.modelo.user.User;
import com.ei10391048.project.modelo.user.UserFacade;
import com.ei10391048.project.modelo.user.UserManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InformationLocationManagerTest {

    static LocationManager manager;
    static InformationLocationManager informationLocationManager;

    static User user;
    @BeforeAll
    static void setUp() throws NotSavedException, IncorrectLocationException, IncorrectUserException {
        //UserManager.getInstance().registerUser("test1@gmail.com","123456");
        user = new User();
        user.setEmail("test@gmail.com");
        UserManager.getInstance().getUserList().add(user);
        user = UserManager.getInstance().getUser("test@gmail.com");
        manager = user.getLocationManager();
        informationLocationManager = user.getInformationLocationManager();
        manager.addUserLocation("Castellón");
        manager.addUserLocation("Madrid");
    }

    @AfterAll
    static void delete(){
        UserManager.getInstance().deleteAllUsers();
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
            informationLocationManager.changeApiState(i,user);
            informationLocationManager.changeApiState(i,user);
            assertTrue(apiList.get(i).getIsActive());
        }
    }
    @Test
    public void activateAPIInvalidCase() throws NotExistingAPIException {
        for(int i=0;i<informationLocationManager.getApis().size();i++){
            informationLocationManager.changeApiState(i,user);
        }
        try{
            informationLocationManager.changeApiState(-1,user);
            fail();
        }catch(NotExistingAPIException ex){
            for(int i=0;i<informationLocationManager.getApis().size();i++) {
                assertFalse(informationLocationManager.getApis().get(i).getIsActive());
                informationLocationManager.changeApiState(i, user);
            }
        }
    }

    @Test
    public void changeApiStateTest() throws NotExistingAPIException {
        List<API> apiList = informationLocationManager.getApis();
        for (int i = 0; i<apiList.size();i++){
            API api = apiList.get(i);
            Location location = manager.getActiveLocations().get(0);
            assertEquals(api.getIsActive(), location.getApiManager().getApiList().get(i).getIsActive());
            informationLocationManager.changeApiState(i,user);
            assertEquals(api.getIsActive(), location.getApiManager().getApiList().get(i).getIsActive());
            informationLocationManager.changeApiState(i,user);
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
            informationLocationManager.changeApiState(i,user);
            assertFalse(apiList.get(i).getIsActive());
            informationLocationManager.changeApiState(i,user);
        }
    }
    @Test
    public void deactivateAPIInvalidCase(){
        try{
            informationLocationManager.changeApiState(informationLocationManager.getApis().size(),user);
            fail();
        }catch(NotExistingAPIException ex){
            for(API api:informationLocationManager.getApis()){
                assertTrue(api.getIsActive());
            }
        }
    }

    @Test
    void activateLocationValidCase() {
        Location location = manager.getLocations().get(0);
        location.setActive(!location.getIsActive());
        location.setActive(!location.getIsActive());
        assertTrue(manager.getLocations().get(0).getIsActive());
    }

}
