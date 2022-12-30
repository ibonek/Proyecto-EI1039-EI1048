package com.ei10391048.project.fireBase;

import com.ei10391048.project.exception.AlreadyExistentUser;
import com.ei10391048.project.exception.IncorrectLocationException;
import com.ei10391048.project.exception.IncorrectUserException;
import com.ei10391048.project.exception.NotSavedException;
import com.ei10391048.project.modelo.Location;
import com.ei10391048.project.modelo.api.API;
import com.ei10391048.project.modelo.api.OpenWeather;
import com.ei10391048.project.modelo.user.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

public class FireBaseTest {

    private static CRUDFireBase crudFireBase;
    private static User user;

    @BeforeAll
    public static void setUp() {
        user = new User();
        user.setEmail("test@gmail.com");
        user.setPassword("123456");
        crudFireBase = new CRUDFireBase();
        try {
            crudFireBase.signUp(user.getEmail(), user.getPassword());
            //sleep(1000);
        } catch (IncorrectUserException | AlreadyExistentUser e) {
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
    public void signUpValid() {
        try {
            crudFireBase.signUp("test1@gmail.com",user.getPassword());
            assertTrue(true);
        } catch (IncorrectUserException | AlreadyExistentUser e) {
            fail();
        }
    }

    @ParameterizedTest
    @MethodSource("provideInvalidUsers")
    public void signUpInvalid(String email, String password) {
        try {
            crudFireBase.signUp(email, password);
            fail();
        } catch (IncorrectUserException | AlreadyExistentUser e) {
            assertTrue(true);
        }
    }

    @Test
    public void deleteUserValid() {
        try {
            crudFireBase.signUp("test4@gmail.com",user.getPassword());
            crudFireBase.deleteUser("test4@gmail.com");
            assertTrue(true);
        } catch (IncorrectUserException e) {
            fail();
        } catch (AlreadyExistentUser e) {
            throw new RuntimeException(e);
        }
    }

    @ParameterizedTest
    @MethodSource("provideInvalidEmails")
    public void deleteUserInvalid(String email) {
        try {
            crudFireBase.deleteUser(email);
            fail();
        } catch (IncorrectUserException e) {
            assertTrue(true);
        }
    }

    public static Stream<Arguments> provideInvalidEmails() {
        return Stream.of(
                Arguments.of((Object) null),
                Arguments.of("test3@gmail.com")
        );
    }

    public static Stream<Arguments> provideInvalidUsers() {
        return Stream.of(
                Arguments.of(null, "123456"),
                Arguments.of("test@gmail.com", null)
        );
    }

    @Test
    public void addUserLocationToBBDDValid(){
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Location location = new Location("Teruel", 40.345, -0.6667);
        try {
            crudFireBase.addUserLocation(location,user.getEmail());
            assertTrue(true);
        } catch (NotSavedException exception){
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
        }
    }

    @Test
    public void getUserLocationFromBBDDValid(){
        Location location = new Location("Teruel", 40.345, -0.6667);
        try {
            crudFireBase.addUserLocation(location, user.getEmail());
            assertEquals(location, crudFireBase.getUserLocation(user.getEmail(), location.getName()));
        } catch (NotSavedException e) {
            fail();
        }
    }

    @ParameterizedTest
    @MethodSource("locations")
    public void getUserLocationFromBBDDInvalid(String locationName){
        assertNull(crudFireBase.getUserLocation(user.getEmail(), locationName));
    }

    static Stream<Arguments> locations(){
        return Stream.of(
                Arguments.of("Valencia"),
                Arguments.of((Object) null)
        );
    }

    @Test
    public void deleteUserLocationsFromBBDDValid(){
        Location location = new Location("Teruel", 40.345, -0.6667);
        try {
            crudFireBase.addUserLocation(location, user.getEmail());
            crudFireBase.deleteUserLocations(user.getEmail());
            sleep(1000);
            assertNull(crudFireBase.getUserLocations(user.getEmail()));
        } catch (NotSavedException | IncorrectLocationException e){
            fail();
        } catch (IncorrectUserException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @ParameterizedTest
    @MethodSource("provideInvalidEmails")
    public void deleteUserLocationsFromBBDDInvalid(String email){
        try {
            crudFireBase.deleteUserLocations(email);
        } catch (IncorrectUserException e) {
            assertTrue(true);
        }
    }

    @Test
    public void deleteUserLocationValid() {
        Location location = new Location("Teruel", 40.345, -0.6667);
        Location location1 = new Location("Benicarló", 39.4699, -0.3774);
        try {
            crudFireBase.addUserLocation(location, user.getEmail());
            crudFireBase.addUserLocation(location1, user.getEmail());
            int size = crudFireBase.getUserLocations(user.getEmail()).size();
            sleep(1000);
            crudFireBase.deleteUserLocation(user.getEmail(), location.getName());
            sleep(1000);
            assertEquals(size-1, crudFireBase.getUserLocations(user.getEmail()).size());
        } catch (NotSavedException | IncorrectLocationException e) {
            fail();
        } catch (InterruptedException e) {
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
                Arguments.of("Teruel"),
                Arguments.of((Object) null)
        );
    }

    @Test
    public void getUserLocationsFromBBDDValid(){
        try {
            assertEquals(1, crudFireBase.getUserLocations(user.getEmail()).size());
        } catch (IncorrectLocationException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void activateUserLocationFromBBDDValid(){
        Location location = new Location("Teruel", 40.345, -0.6667);
        try {
            location.setActive(false);
            crudFireBase.addUserLocation(location, user.getEmail());
            crudFireBase.changeUserLocationStatus(user.getEmail(), location);
            assertTrue(crudFireBase.getUserLocation(user.getEmail(),location.getName()).getIsActive());
        } catch (NotSavedException exception) {
            fail();
        }
    }

    @ParameterizedTest
    @MethodSource("status")
    public void activateUserLocationFromBBDDInvalid(Location status) {
        try {
            crudFireBase.changeUserLocationStatus(user.getEmail(), status);
            fail();
        } catch (NotSavedException e) {
            assertTrue(true);
        }
    }

    static Stream<Arguments> status(){
        return Stream.of(
                Arguments.of((Object) null),
                Arguments.of(new Location("Madrid", 40.345, -0.6667))
        );
    }

    @Test
    public void deactivateLocationFromBBDDValid(){
        Location location = new Location("Teruel", 40.345, -0.6667);
        try {
            crudFireBase.addUserLocation(location, user.getEmail());
            crudFireBase.changeUserLocationStatus(user.getEmail(), location);
            assertFalse(crudFireBase.getUserLocation(user.getEmail(), location.getName()).getIsActive());
        } catch (NotSavedException exception) {
            fail();
        }
    }

    @ParameterizedTest
    @MethodSource("status")
    public void deactivateLocationFromBBDDInvalid(Location status) {
        try {
            crudFireBase.changeUserLocationStatus(user.getEmail(), status);
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
            assertTrue(crudFireBase.getAPI(user.getEmail(),api.getName()).getIsActive());
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
            assertTrue(crudFireBase.getAPI(user.getEmail(),api.getName()).getIsActive());
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
        API api= new OpenWeather();
        Location location = new Location("Teruel", 40.345, -0.6667);
        try {
            crudFireBase.addUserLocation(location, user.getEmail());
            api.setActive(false);
            crudFireBase.changeUserLocationAPIStatus(user.getEmail(), location.getName(), api);
            assertTrue(crudFireBase.getUserLocationAPIs(user.getEmail(), location).get(0).getIsActive());
        } catch (NotSavedException exception) {
            fail();
        } catch (IncorrectLocationException e) {
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
        Location location = new Location("Teruel", 40.345, -0.6667);
        try {
            crudFireBase.addUserLocation(location, user.getEmail());
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
