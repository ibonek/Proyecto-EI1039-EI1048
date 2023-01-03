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

    @Test
    public void addUserValid() {
        try {
            crudFireBase.addUser("tes2@gmail.com");
            assertEquals(crudFireBase.getUser(user.getEmail()).getEmail(), user.getEmail());
        } catch (IncorrectUserException | InterruptedException e) {
            fail();
        }
    }

    @Test
    public void addUserInvalid() {
       assertThrows(IncorrectUserException.class, () -> crudFireBase.addUser(null));
    }

    @Test
    public void deleteUserValid() {
        try {
            crudFireBase.deleteUser("test4@gmail.com");
            assertTrue(true);
        } catch (IncorrectUserException e) {
            fail();
        }
    }

    @Test
    public void deleteUserInvalid() {
        try {
            crudFireBase.deleteUser(null);
            fail();
        } catch (IncorrectUserException e) {
            assertTrue(true);
        }
    }

    @Test
    public void addUserLocationToBBDDValid(){
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

    @Test
    public void addUserLocationToBBDDInvalid(){
        try {
            crudFireBase.addUserLocation(null, user.getEmail());
            fail();
        } catch (NotSavedException exception){
            assertTrue(true);
        } catch (AlreadyExistentLocationException | ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

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

    @ParameterizedTest
    @MethodSource("locations")
    public void getUserLocationFromBBDDInvalid(String locationName){
        try {
            assertNull(crudFireBase.getUserLocation(user.getEmail(), locationName));
        } catch (ExecutionException | InterruptedException | IncorrectLocationException e) {
            fail();
        }
    }

    static Stream<Arguments> locations(){
        return Stream.of(
                Arguments.of("Valencia"),
                Arguments.of((Object) null)
        );
    }

    @Test
    public void deleteUserLocationValid() {
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

    @ParameterizedTest
    @MethodSource("deleteLocations")
    public void deleteUserLocationInvalid(String locationName){
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

    @Test
    public void getUserLocationsFromBBDDValid(){
        try {
            crudFireBase.deleteUserLocations(user.getEmail());
            crudFireBase.addUserLocation(location, user.getEmail());
            assertEquals(1, crudFireBase.getUserLocations(user.getEmail()).size());
        } catch (IncorrectLocationException | IncorrectUserException | AlreadyExistentLocationException |
                 NotSavedException | ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void activateUserLocationFromBBDDValid(){
        try {
            location.setActive(false);
            crudFireBase.changeUserLocationStatus(user.getEmail(), location);
            sleep(1000);
            assertTrue(crudFireBase.getUserLocation(user.getEmail(),location.getName()).getIsActive());
        } catch (NotSavedException | ExecutionException | InterruptedException | IncorrectLocationException exception) {
            fail();
        }
    }

    @Test
    public void activateUserLocationFromBBDDInvalid() {
        try {
            crudFireBase.changeUserLocationStatus(user.getEmail(), null);
            fail();
        } catch (NotSavedException e) {
            assertTrue(true);
        }
    }


    @Test
    public void deactivateLocationFromBBDDValid(){
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

    @Test
    public void deactivateLocationFromBBDDInvalid() {
        try {
            crudFireBase.changeUserLocationStatus(user.getEmail(), null);
            fail();
        } catch (NotSavedException e) {
            assertTrue(true);
        }
    }

    @Test
    public void activateAPIValid(){
        API api= new OpenWeather();
        try {
            api.setActive(false);
            crudFireBase.changeAPIStatus(user.getEmail(),api);
            assertTrue(crudFireBase.getUserAPI(user.getEmail(),api.getName()).getIsActive());
        } catch (NotSavedException exception) {
            fail();
        }
    }

    @Test
    public void activateAPIInvalid() {
        try {
            crudFireBase.changeAPIStatus(user.getEmail(),null);
            fail();
        } catch (NotSavedException e) {
            assertTrue(true);
        }
    }

    @Test
    public void deactivateAPIValid(){
        API api= new OpenWeather();
        try {
            api.setActive(false);
            crudFireBase.changeAPIStatus(user.getEmail(),api);
            assertTrue(crudFireBase.getUserAPI(user.getEmail(),api.getName()).getIsActive());
        } catch (NotSavedException exception) {
            fail();
        }
    }

    @Test
    public void deactivateAPIInvalid() {
        try {
            crudFireBase.changeAPIStatus(user.getEmail(),null);
            fail();
        } catch (NotSavedException e) {
            assertTrue(true);
        }
    }

    @Test
    public void activateAPILocationValid(){
        int order = APIsNames.WEATHER.getOrder();
        API api= location.getApiManager().getApiList().get(order);
        api.setActive(false);
        try {
            crudFireBase.changeUserLocationAPIStatus(user.getEmail(), location.getName(), api);
            sleep(1000);
            List<API> list = crudFireBase.getUserLocationAPIs(user.getEmail(), location);

            System.out.println(list.get(0).getName() +" "+list.get(0).getIsActive());
            assertTrue(list.get(order).getIsActive());
        } catch (NotSavedException exception) {
            fail();
        } catch (IncorrectLocationException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @ParameterizedTest
    @MethodSource("APILocation")
    public void activateAPILocationInvalid(API api, String locationName) {
        try {
            crudFireBase.changeUserLocationAPIStatus(user.getEmail(), locationName, api);
            fail();
        } catch (NotSavedException e) {
            assertTrue(true);
        }
    }

    @Test
    public void deactivateAPILocationValid(){
        API api= new OpenWeather();
        try {
            crudFireBase.changeUserLocationAPIStatus(user.getEmail(), location.getName(), api);
            assertFalse(crudFireBase.getUserLocationAPI(user.getEmail(), location.getName(), api.getName()).getIsActive());
        } catch (NotSavedException exception) {
            fail();
        }
    }

    @ParameterizedTest
    @MethodSource("APILocation")
    public void deactivateAPILocationInvalid(API api, String location) {
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
}
