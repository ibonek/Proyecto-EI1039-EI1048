package com.ei10391048.project.fireBase;

import com.ei10391048.project.exception.*;
import com.ei10391048.project.modelo.Location;
import com.ei10391048.project.modelo.api.API;
import com.ei10391048.project.modelo.api.APIsNames;
import com.ei10391048.project.modelo.api.OpenWeather;
import com.ei10391048.project.modelo.user.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

public class FireBaseTest {

    private static CRUDFireBase crudFireBase;
    private static User user;
    private static Location location;

    @BeforeAll
    public static void setUp() {
        user = new User();
        user.setEmail("test@gmail.com");

        crudFireBase = CRUDFireBase.getInstance();
        try {
            crudFireBase.deleteUsers();
            crudFireBase.addUser(user.getEmail());
            location = new Location("Teruel", 39.0, -1.0);
            crudFireBase.addUserLocation(location, user.getEmail());
            sleep(1000);
        } catch (IncorrectUserException | InterruptedException | AlreadyExistentLocationException | NotSavedException |
                 ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterAll
    public static void delete(){
        try {
            crudFireBase.deleteUsers();
        } catch (IncorrectUserException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Test que comprueba la historia de usuario 1: Como usuario quiero registrarme usando un correo electrónico
     * y contraseña para que mis datos se queden almacenados.
     */
    @Test
    public void addUserValidCase() {
        try {
            crudFireBase.addUser("tes2@gmail.com");
            assertEquals(crudFireBase.getUser(user.getEmail()).getEmail(), user.getEmail());
        } catch (IncorrectUserException | InterruptedException e) {
            fail();
        }
    }

    /**
     * Test que comprueba la historia de usuario 1: Como usuario quiero registrarme usando un correo electrónico
     * y contraseña para que mis datos se queden almacenados.
     */
    @Test
    public void addUserInvalidCase() {
       assertThrows(IncorrectUserException.class, () -> crudFireBase.addUser(null));
    }

    /**
     * Test que comprueba la historia de usuario 4: Como usuario quiero eliminar la cuenta para que se borren mis datos del sistema.
     */
    @Test
    public void deleteUserValidCase() {
        try {
            crudFireBase.addUser("test4@gmail.com");
            sleep(1000);
            crudFireBase.deleteUser("test4@gmail.com");
            assertTrue(true);
        } catch (IncorrectUserException | InterruptedException e) {
            fail();
        }
    }

    /**
     * Test que comprueba la historia de usuario 4: Como usuario quiero eliminar la cuenta para que se borren mis datos del sistema.
     */
    @Test
    public void deleteUserInvalidCase() {
        try {
            crudFireBase.deleteUser(null);
            fail();
        } catch (IncorrectUserException e) {
            assertTrue(true);
        }
    }

    /**
     * Test que comprueba la historia de usuario 23.1: Como usuario quiero que cada vez que active una ubicación estos
     * cambios se almacenen en la base de datos para evitar que se pierdan. Escenario 1: La ubicación existe.
     */
    @Test
    public void activateUserLocationFromBBDDValidCase(){
        try {
            location.setActive(false);
            crudFireBase.changeUserLocationStatus(user.getEmail(), location);
            sleep(1000);
            assertTrue(crudFireBase.getUserLocation(user.getEmail(),location.getName()).getIsActive());
        } catch (NotSavedException | ExecutionException | InterruptedException | IncorrectLocationException exception) {
            fail();
        }
    }

    /**
     * Test que comprueba la historia de usuario 23.1: Como usuario quiero que cada vez que active una ubicación estos
     * cambios se almacenen en la base de datos para evitar que se pierdan. Escenario 2: La ubicación no existe.
     */
    @Test
    public void activateUserLocationFromBBDDInvalidCase() {
        try {
            crudFireBase.changeUserLocationStatus(user.getEmail(), null);
            fail();
        } catch (NotSavedException e) {
            assertTrue(true);
        }
    }

    /**
     * Test que comprueba la historia de usuario 23.2: Como usuario quiero que cada vez que desactive una ubicación
     * estos cambios se almacenen en la base de datos para evitar que se pierdan. Escenario 1: La ubicación existe.
     */
    @Test
    public void deactivateLocationFromBBDDValidCase(){
        try {
            crudFireBase.changeUserLocationStatus(user.getEmail(), location);
            sleep(1000);
            Location location1 = crudFireBase.getUserLocation(user.getEmail(), location.getName());
            assertFalse(location1.getIsActive());
        } catch (NotSavedException | ExecutionException | InterruptedException | IncorrectLocationException exception) {
            exception.printStackTrace();
            fail();
        }
    }

    /**
     * Test que comprueba la historia de usuario 23.2: Como usuario quiero que cada vez que desactive una ubicación
     * estos cambios se almacenen en la base de datos para evitar que se pierdan. Escenario 2: La ubicación no existe.
     */
    @Test
    public void deactivateLocationFromBBDDInvalidCase() {
        try {
            crudFireBase.changeUserLocationStatus(user.getEmail(), null);
            fail();
        } catch (NotSavedException e) {
            assertTrue(true);
        }
    }

    /**
     * Test que comprueba la historia de usuario 23.3: Como usuario quiero que cada vez que dé de alta una ubicación
     * estos cambios se almacenen en la base de datos para evitar que se pierdan. Escenario 1: La ubicación no existe.
     */
    @Test
    public void addUserLocationToBBDDValidCase(){
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Location location = new Location("test", 40.345, -0.6667);
        try {
            crudFireBase.addUserLocation(location,user.getEmail());
            assertTrue(true);
        } catch (NotSavedException | AlreadyExistentLocationException | ExecutionException | InterruptedException exception){
            fail();
        }
    }
    /**
     * Test que comprueba la historia de usuario 23.3: Como usuario quiero que cada vez que dé de alta una ubicación
     * estos cambios se almacenen en la base de datos para evitar que se pierdan. Escenario 2: La ubicación ya existe.
     */
    @Test
    public void addUserLocationToBBDDInvalidCase(){
        try {
            crudFireBase.addUserLocation(null, user.getEmail());
            fail();
        } catch (NotSavedException exception){
            assertTrue(true);
        } catch (AlreadyExistentLocationException | ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    static Stream<Arguments> locations(){
        return Stream.of(
                Arguments.of("Valencia"),
                Arguments.of((Object) null)
        );
    }

    /**
     * Test que comprueba la historia de usuario 23.4: Como usuario quiero que cada vez que dé de baja una ubicación
     * estos cambios se almacenen en la base de datos para evitar que se pierdan. Escenario 1: La ubicación existe.
     */
    @Test
    public void deleteUserLocationValidCase() {
        Location location1 = new Location("Benicarló", 39.4699, -0.3774);
        try {
            crudFireBase.addUserLocation(location1, user.getEmail());
            int size = crudFireBase.getUserLocations(user.getEmail()).size();
            sleep(1000);
            crudFireBase.deleteUserLocation(user.getEmail(), location1.getName());
            sleep(1000);
            assertEquals(size-1, crudFireBase.getUserLocations(user.getEmail()).size());
        } catch (NotSavedException | IncorrectLocationException e) {
            fail();
        } catch (InterruptedException | AlreadyExistentLocationException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Test que comprueba la historia de usuario 23.4: Como usuario quiero que cada vez que dé de baja una ubicación
     * estos cambios se almacenen en la base de datos para evitar que se pierdan. Escenario 2: La ubicación no existe.
     */
    @ParameterizedTest
    @MethodSource("deleteLocations")
    public void deleteUserLocationInvalidCase(String locationName){
        try {
            crudFireBase.deleteUserLocation(user.getEmail(), locationName);
            fail();
        } catch (IncorrectLocationException e) {
            assertTrue(true);
        }
    }

    public static Stream<Arguments> deleteLocations() {
        return Stream.of(
                Arguments.of("Benicarloo"),
                Arguments.of((Object) null)
        );
    }

    /**
     * Test que comprueba la historia de usuario 23.5: Como usuario quiero que cada vez que se active una API genérica
     * estos cambios se almacenen en la base de datos para evitar que se pierdan. Escenario 1: La API existe.
     */
    @Test
    public void activateAPIValidCase(){
        API api= new OpenWeather();
        try {
            api.setActive(false);
            crudFireBase.changeAPIStatus(user.getEmail(),api);
            assertTrue(crudFireBase.getUserAPI(user.getEmail(),api.getName()).getIsActive());
        } catch (NotSavedException exception) {
            fail();
        }
    }

    /**
     * Test que comprueba la historia de usuario 23.5: Como usuario quiero que cada vez que se active una API genérica
     * estos cambios se almacenen en la base de datos para evitar que se pierdan. Escenario 2: La API no existe.
     */
    @Test
    public void activateAPIInvalidCase() {
        try {
            crudFireBase.changeAPIStatus(user.getEmail(),null);
            fail();
        } catch (NotSavedException e) {
            assertTrue(true);
        }
    }


    /**
     * Test que comprueba la historia de usuario 23.6: Como usuario quiero que cada vez que se desactive una API
     * genérica estos cambios se almacenen en la base de datos para evitar que se pierdan. Escenario 1: La API existe.
     */
    @Test
    public void deactivateAPIValidCase(){
        API api= new OpenWeather();
        try {
            api.setActive(true);
            crudFireBase.changeAPIStatus(user.getEmail(),api);
            assertFalse(crudFireBase.getUserAPI(user.getEmail(),api.getName()).getIsActive());
        } catch (NotSavedException exception) {
            fail();
        }
    }

    /**
     * Test que comprueba la historia de usuario 23.6: Como usuario quiero que cada vez que se desactive una API
     * genérica estos cambios se almacenen en la base de datos para evitar que se pierdan. Escenario 2: La API no existe.
     */
    @Test
    public void deactivateAPIInvalidCase() {
        try {
            crudFireBase.changeAPIStatus(user.getEmail(),null);
            fail();
        } catch (NotSavedException e) {
            assertTrue(true);
        }
    }

    /**
     * Test que comprueba la historia de usuario 23.7: Como usuario quiero que cada vez que se active un servicio de una
     * ubicación específica estos cambios se almacenen en la base de datos para evitar que se pierdan. Escenario 1: La
     * ubicación existe y la API existen.
     */
    @Test
    public void activateAPILocationValidCase(){
        int order = APIsNames.WEATHER.getOrder();
        API api= location.getApiManager().getApiList().get(order);
        api.setActive(false);
        try {
            crudFireBase.changeUserLocationAPIStatus(user.getEmail(), location.getName(), api);
            sleep(1000);
            List<API> list = crudFireBase.getUserLocationAPIs(user.getEmail(), location);
            assertTrue(list.get(order).getIsActive());
        } catch (NotSavedException exception) {
            fail();
        } catch (IncorrectLocationException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Test que comprueba la historia de usuario 23.7: Como usuario quiero que cada vez que se active un servicio de una
     * ubicación específica estos cambios se almacenen en la base de datos para evitar que se pierdan. Escenario 2: La
     * API no existe.
     */
    @ParameterizedTest
    @MethodSource("APILocation")
    public void activateAPILocationInvalidCase(API api, String locationName) {
        try {
            crudFireBase.changeUserLocationAPIStatus(user.getEmail(), locationName, api);
            fail();
        } catch (NotSavedException e) {
            assertTrue(true);
        }
    }

    /**
     * Test que comprueba la historia de usuario 23.8: Como usuario quiero que cada vez que se desactive un servicio de
     * una ubicación específica estos cambios se almacenen en la base de datos para evitar que se pierdan. Escenario 1:
     * la ubicación existe y la API existen.
     */
    @Test
    public void deactivateAPILocationValidCase(){
        API api= new OpenWeather();
        try {
            crudFireBase.changeUserLocationAPIStatus(user.getEmail(), location.getName(), api);
            assertFalse(crudFireBase.getUserLocationAPI(user.getEmail(), location.getName(), api.getName()).getIsActive());
        } catch (NotSavedException exception) {
            fail();
        }
    }

    /**
     * Test que comprueba la historia de usuario 23.8: Como usuario quiero que cada vez que se desactive un servicio de
     * una ubicación específica estos cambios se almacenen en la base de datos para evitar que se pierdan. Escenario 2:
     * La API no existe.
     */
    @ParameterizedTest
    @MethodSource("APILocation")
    public void deactivateAPILocationInvalidCase(API api, String location) {
        try {
            crudFireBase.changeUserLocationAPIStatus(user.getEmail(), location, api);
            fail();
        } catch (NotSavedException e) {
            assertTrue(true);
        }
    }

    static Stream<Arguments> APILocation(){
        return Stream.of(
                Arguments.of(null, "Teruel")
        );
    }

    /**
     * Test de unidad valido
     */
    @Test
    public void getUserLocationFromBBDDValid(){
        try {
            Location location1=new Location("Casa",300,100);
            crudFireBase.addUserLocation(location1, user.getEmail());
            assertEquals(location1.getName(), crudFireBase.getUserLocation(user.getEmail(), location1.getName()).getName());
        } catch (ExecutionException | InterruptedException | AlreadyExistentLocationException | NotSavedException |
                IncorrectLocationException e) {
            fail();
        }
    }

    /**
     * Test de unidad invalido
     */
    @ParameterizedTest
    @MethodSource("locations")
    public void getUserLocationFromBBDDInvalid(String locationName){
        try {
            assertNull(crudFireBase.getUserLocation(user.getEmail(), locationName));
        } catch (ExecutionException | InterruptedException | IncorrectLocationException e) {
            fail();
        }
    }
}
