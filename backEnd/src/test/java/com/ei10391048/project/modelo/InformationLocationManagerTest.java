package com.ei10391048.project.modelo;

import com.ei10391048.project.exception.IncorrectLocationException;
import com.ei10391048.project.exception.IncorrectUserException;
import com.ei10391048.project.exception.NotExistingAPIException;
import com.ei10391048.project.exception.NotSavedException;
import com.ei10391048.project.modelo.api.API;
import com.ei10391048.project.modelo.api.APIsNames;
import com.ei10391048.project.modelo.user.User;
import com.ei10391048.project.modelo.user.UserManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InformationLocationManagerTest {

    static LocationManager manager;
    static InformationLocationManager informationLocationManager;

    static User user;
    @BeforeAll
    static void setUp() throws IncorrectLocationException, IncorrectUserException {
        user = new User();
        user.setEmail("test@gmail.com");
        UserManager.getInstance().getUserList().add(user);
        user = UserManager.getInstance().getUser("test@gmail.com");
        manager = user.getLocationManager();
        informationLocationManager = user.getInformationLocationManager();
        Location location = new Location("Castellón", 39.46975, -0.37739);
        Location location2 = new Location("Madrid", 40.416775, -3.70379);
        manager.addUserLocation(location);
        manager.addUserLocation(location2);
    }

    @AfterAll
    static void delete(){
        UserManager.getInstance().deleteAllUsers();
    }

    /**
     * Test que comprueba la historia de usuario 10: Como usuario quiero activar
     * una ubicación disponible en el sistema, con el fin de recibir información
     * relacionada con dicha ubicación. Escenario 1: La ubicación está desactivada.
     */
    @Test
    void activateLocationValidCase() {
        Location location = manager.getUserLocations().get(0);
        location.setActive(!location.getIsActive());
        location.setActive(!location.getIsActive());
        assertTrue(manager.getUserLocations().get(0).getIsActive());
    }


    /**
     * Test que comprueba la historia de usuario 12: Como usuario quiero desactivar una ubicación activa, con el fin de reducir temporalmente la cantidad de
     * información a consultar. Escenario 1: La ubicación está activa.
     */
    @Test
    void desactivateLocationValidCase() {
        Location location = manager.getUserLocations().get(0);
        location.setActive(!location.getIsActive());
        location.setActive(!location.getIsActive());
        assertTrue(manager.getUserLocations().get(0).getIsActive());
    }

    /**
     * Test que comprueba la historia de usuario 19: Como usuario quiero consultar
     * la lista de servicios de información disponibles (API).
     */
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
     Test que comprueba la historia de usuario 20: Como usuario quiero activar un
     servicio de información (API), entre aquellos disponibles. Escenario 1: La API
     está desactivada.
     */
    @Test
    public void activateAPIValidCase() {
        List<API> apiList = informationLocationManager.getApis();
        assertTrue(apiList.get(0).getIsActive());
    }

    /**
     Test que comprueba la historia de usuario 20: Como usuario quiero activar un
     servicio de información (API), entre aquellos disponibles. Escenario 2: La API
     está activada.
     */
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

    /**
     * Test unidad comprueba el cambio de estado de las APIs de las historias 20 y 22
     */
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
     * Test que comprueba la historia de usuario 22: Como usuario quiero desactivar
     * un servicio de información que haya dejado de interesar, con el fin de evitar
     * interfaces de usuario sobrecargadas. Escenario 1: La API está activada.
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

    /**
     * Test que comprueba la historia de usuario 22: Como usuario quiero desactivar
     * un servicio de información que haya dejado de interesar, con el fin de evitar
     * interfaces de usuario sobrecargadas. Escenario 2: La API está desactivada.
     */
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
}
