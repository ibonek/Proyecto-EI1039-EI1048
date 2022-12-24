package com.ei10391048.project.modelo;

import com.ei10391048.project.exception.IncorectAliasException;
import com.ei10391048.project.exception.IncorrectLocationException;
import com.ei10391048.project.exception.NotSavedException;
import com.ei10391048.project.modelo.api.API;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Collections.addAll;
import static org.junit.jupiter.api.Assertions.*;

class LocationManagerTest {
    @BeforeEach
    void setUp(){
        LocationManager manager = LocationManager.getInstance();
        manager.clearLocations();
    }


    @ParameterizedTest
    @MethodSource("getName")
    void getLocationsNameValidCase(ArrayList<String> sol) throws IncorrectLocationException, NotSavedException {
        LocationManager manager = LocationManager.getInstance();
        for (String name : sol) {
            GeoCodService geoCodService = new GeoCodService();
            geoCodService.setSearch(new ByName(name));
            manager.setLocationApi(geoCodService);
            manager.addLocation();

        }

        assertEquals(manager.getActiveLocationsName(), sol);
    }

    static Stream<Arguments> getName() {

        ArrayList<String> sol = new ArrayList<>();
        addAll(sol, "Valencia","Madrid","Beijing");
        ArrayList<String> sol2 = new ArrayList<>();
        addAll(sol2, "Montevideo","Castellon","London");
        return Stream.of(
                Arguments.of( sol),
                Arguments.of( sol2)
        );
    }

    @Test
    void getLocationsNameInvalidCase() {
        LocationManager manager = LocationManager.getInstance();
        manager.setLocations(new LinkedList<>());
        assertEquals(manager.getLocations(), new LinkedList<Location>());
    }

    @Test
    void activateLocationValidCase() throws IncorrectLocationException, NotSavedException{
        LocationManager manager = LocationManager.getInstance();
        GeoCodService geoCodService = new GeoCodService();
        geoCodService.setSearch(new ByName("Castellón"));
        manager.setLocationApi(geoCodService);
        manager.addLocation();
        geoCodService.setSearch(new ByName("Madrid"));
        manager.setLocationApi(geoCodService);
        manager.addLocation();
        manager.getLocations().get(1).setActive(false);
        manager.changeActiveState("Madrid");
        assertTrue(manager.getLocations().get(1).getIsActive());
    }

    @Test
    void activateLocationInvalidCase() throws NotSavedException {
        try {
            LocationManager manager = LocationManager.getInstance();
            GeoCodService geoCodService = new GeoCodService();
            geoCodService.setSearch(new ByName("Castellón"));
            manager.setLocationApi(geoCodService);
            manager.addLocation();
            geoCodService.setSearch(new ByName("Madrid"));
            manager.setLocationApi(geoCodService);
            manager.addLocation();
            manager.getLocations().get(1).setActive(false);

            manager.changeActiveState("Castellonn");
            fail();
        } catch (IncorrectLocationException e){
            assertTrue(true);
        }
    }

    @ParameterizedTest
    @MethodSource("getActiveLocation")
    void getActiveLocationValidCase(ArrayList<String> sol) throws IncorrectLocationException,  NotSavedException {
        LocationManager manager = LocationManager.getInstance();
        GeoCodService geoCodService = new GeoCodService();
        for (String name : sol) {
            geoCodService.setSearch(new ByName(name));
            manager.setLocationApi(geoCodService);
            manager.addLocation();
        }
        assertEquals(manager.getActiveLocationsName(), sol);
    }

    static Stream<Arguments> getActiveLocation() {

        ArrayList<String> sol = new ArrayList<>();
        addAll(sol, "Valencia","Madrid","Beijing");
        ArrayList<String> sol2 = new ArrayList<>();
        addAll(sol2, "Montevideo","Castellon","London");
        return Stream.of(
                Arguments.of( sol),
                Arguments.of( sol2)
        );
    }

    @ParameterizedTest
    @MethodSource("getActiveLocationInvalid")
    void getActiveLocationInvalidCase(ArrayList<String> input) throws IncorrectLocationException, NotSavedException {
        LocationManager manager = LocationManager.getInstance();
        GeoCodService geoCodService = new GeoCodService();
        for (String name : input) {
            geoCodService.setSearch(new ByName(name));
            manager.setLocationApi(geoCodService);
            manager.addLocation();
        }


        assertNotEquals(manager.getActiveLocationsName().size(), 0);

    }

    static Stream<Arguments> getActiveLocationInvalid() {

        ArrayList<String> input = new ArrayList<>();
        addAll(input, "Valencia","Madrid","Beijing");
        ArrayList<String> input2 = new ArrayList<>();
        addAll(input2, "Montevideo","Castellon","London");
        return Stream.of(
                Arguments.of(input),
                Arguments.of(input2)
        );
    }

    @ParameterizedTest
    @MethodSource("getInactiveLocationValidCase")
    void getInactiveLocationValidCase(ArrayList<String> input) throws IncorrectLocationException, NotSavedException {
        LocationManager manager = LocationManager.getInstance();
        GeoCodService geoCodService = new GeoCodService();
        for (String name : input) {
            geoCodService.setSearch(new ByName(name));
            manager.setLocationApi(geoCodService);
            manager.addLocation();
            manager.changeActiveState(name);
            assertNotEquals(manager.getLocation(name).getIsActive(), true);
        }

    }

    static Stream<Arguments> getInactiveLocationValidCase() {

        ArrayList<String> input = new ArrayList<>();
        addAll(input, "Valencia","Madrid","Beijing");
        ArrayList<String> input2 = new ArrayList<>();
        addAll(input2, "Montevideo","Castellon","London");
        return Stream.of(
                Arguments.of(input),
                Arguments.of(input2)
        );
    }

    @ParameterizedTest
    @MethodSource("getValidAlias")
    void getLocationsAliasValidCase(ArrayList<String[]> input, ArrayList<String> sol) throws IncorrectLocationException, NotSavedException, IncorectAliasException {

            LocationManager manager = LocationManager.getInstance();
            for (String[] name : input) {
                GeoCodService geoCodService = new GeoCodService();
                geoCodService.setSearch(new ByName(name[0]));
                manager.setLocationApi(geoCodService);
                manager.addLocation();
                if (name.length==2) {
                    manager.setAlias(name[0], name[1]);
                }
            }
        assertEquals(manager.getLocationsAlias(),sol);
    }

    static Stream<Arguments> getValidAlias() {

        ArrayList<String[]> input = new ArrayList<>();
        addAll(input, new String[]{"Valencia", "casa"},new String[]{"Madrid", "abu"},new String[]{"Beijing"});
        ArrayList<String[]> input2 = new ArrayList<>();
        addAll(input2, new String[]{"Montevideo", "casa"},new String[]{"Castellon", "abu"},new String[]{"London"});
        ArrayList<String> sol = new ArrayList<>();
        addAll(sol, "casa","abu","Beijing");
        ArrayList<String> sol2 = new ArrayList<>();
        addAll(sol2, "casa", "abu","London");
        return Stream.of(
                Arguments.of(input, sol),
                Arguments.of(input2, sol2)
        );
    }

    @ParameterizedTest
    @MethodSource("getInvalidAlias")
    void getLocationsAliasInvalidCase(ArrayList<String> sol) throws IncorrectLocationException, NotSavedException {
        try {
            LocationManager manager = LocationManager.getInstance();
            for (String name : sol) {
                GeoCodService geoCodService = new GeoCodService();
                geoCodService.setSearch(new ByName(name));
                manager.setLocationApi(geoCodService);
                manager.addLocation();
                manager.setAlias(name,"");
            }
            fail();
        } catch (IncorectAliasException e){
            assertTrue(true);
        }
    }

    static Stream<Arguments> getInvalidAlias() {

        ArrayList<String> sol = new ArrayList<>();
        addAll(sol, "Valencia","Madrid","Beijing");
        ArrayList<String> sol2 = new ArrayList<>();
        addAll(sol2, "Montevideo","Castellon","London");
        return Stream.of(
                Arguments.of( sol),
                Arguments.of( sol2)
        );
    }

    @Test
    void deleteLocationValidCase(){
        try {
            LocationManager manager = LocationManager.getInstance();
            GeoCodService geoCodService = new GeoCodService();
            geoCodService.setSearch(new ByName("Valencia"));
            manager.setLocationApi(geoCodService);
            manager.addLocation();
            manager.deleteLocation("Valencia");
            assertTrue(true);
        } catch (IncorrectLocationException e) {
            fail();
        } catch (NotSavedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void deleteLocationInvalidCase(){
        LocationManager manager = LocationManager.getInstance();
        try {
            manager.deleteLocation("Valencia");
            fail();
        } catch (IncorrectLocationException e) {
            assertTrue(true);
        }
    }

    @Test
    void getApiList(){
        LocationManager manager = LocationManager.getInstance();
        List<API> apilist = manager.getApis();
        String[] sol = {"OpenWeather", "TicketMaster", "NewsAPI"};

        assertEquals(apilist.size(), sol.length);

        for (int i = 0; i<apilist.size(); i++){
            assertEquals(apilist.get(i), sol[i]);
        }
    }
}