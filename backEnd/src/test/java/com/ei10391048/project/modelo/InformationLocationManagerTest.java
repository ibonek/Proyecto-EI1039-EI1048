package com.ei10391048.project.modelo;

import com.ei10391048.project.exception.IncorrectLocationException;
import com.ei10391048.project.exception.NonExistingAPIException;
import com.ei10391048.project.exception.NotSavedException;
import com.ei10391048.project.modelo.api.API;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InformationLocationManagerTest {

    LocationManagerFacade manager =  LocationManager.getInstance();
    InformationLocationManagerFacade informationLocationManager = manager.getActivationManager();
    @BeforeEach
    void setUp() throws NotSavedException, IncorrectLocationException {
        manager.addLocation("Castellón");
        manager.addLocation("Madrid");
        manager.clearLocations();
    }
    @Test
    void activateLocationValidCase() throws IncorrectLocationException{
    informationLocationManager.changeActiveState(manager.getLocations().get(1).getAlias());
    assertFalse(manager.getLocations().get(1).getIsActive());
    }

    @Test
    void activateLocationInvalidCase(){
        try {
            informationLocationManager.changeActiveState("Castellonn");
            fail();
        } catch (IncorrectLocationException e){
            assertTrue(true);
        }
    }

    @Test
    void getApiList(){
        List<API> apilist = informationLocationManager.getApis();
        String[] sol = {"OpenWeather", "TicketMaster", "NewsAPI"};

        assertEquals(apilist.size(), sol.length);

        for (int i = 0; i<apilist.size(); i++){
            assertEquals(apilist.get(i).getAPIName(), sol[i]);
        }
    }

    /**
     Test que comprueba la historia de usuario 20: Como usuario quiero activar un servicio de información (API), entre aquellos disponibles
     */
    @Test
    public void activateAPIValidCase() throws NonExistingAPIException {
        List<API> apiList = informationLocationManager.getApis();
        for (int i=0;i<apiList.size();i++){
            assertTrue(apiList.get(i).getIsActive());
            informationLocationManager.changeApiState(i);
            informationLocationManager.changeApiState(i);
            assertTrue(apiList.get(i).getIsActive());
        }
    }
    @Test
    public void activateAPIInvalidCase() throws NonExistingAPIException {
        for(int i=0;i<informationLocationManager.getApis().size();i++){
            informationLocationManager.changeApiState(i);
        }
        try{
            informationLocationManager.changeApiState(-1);
            fail();
        }catch(NonExistingAPIException ex){
            for(API api:informationLocationManager.getApis()){
                assertFalse(api.getIsActive());
            }
        }
    }

    @Test
    public void changeApiStateTest() throws NonExistingAPIException {
        List<API> apiList = informationLocationManager.getApis();
        for (int i = 0; i<apiList.size();i++){
            API api = apiList.get(i);
            Location location = manager.getActiveLocations().get(0);
            assertEquals(api.getIsActive(), location.getApiManager().getApiList().get(i).getIsActive());
            informationLocationManager.changeApiState(i);
            assertEquals(api.getIsActive(), location.getApiManager().getApiList().get(i).getIsActive());
            informationLocationManager.changeApiState(i);
        }
    }
    /**
     * Test que comprueba la historia de usuario 22:  Como usuario quiero desactivar un servicio de información que haya dejado de interesar,
     * con el fin de evitar interfaces de usuario sobrecargadas.
     *
     */

    @Test
    public void deactivateAPIValidCase() throws NonExistingAPIException {
        List<API> apiList = informationLocationManager.getApis();
        for (int i=0;i<apiList.size();i++){
            informationLocationManager.changeApiState(i);
            assertFalse(apiList.get(i).getIsActive());
            informationLocationManager.changeApiState(i);
        }
    }
    @Test
    public void deactivateAPIInvalidCase(){
        try{
            informationLocationManager.changeApiState(informationLocationManager.getApis().size());
            fail();
        }catch(NonExistingAPIException ex){
            for(API api:informationLocationManager.getApis()){
                assertTrue(api.getIsActive());
            }
        }
    }

}
