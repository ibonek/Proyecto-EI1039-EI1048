package com.ei10391048.project.modelo;

import com.ei10391048.project.controlador.InputValidator;
import com.ei10391048.project.exception.IncorrectLocationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;

import java.util.LinkedList;
import java.util.stream.Stream;

import static java.util.Collections.addAll;
import static org.junit.jupiter.api.Assertions.*;

class LocationManagerTest {

    @ParameterizedTest
    @MethodSource("getName")
    void getLocationsNameValidCase(LinkedList<Location> input,ArrayList<String> sol) {
        LocationManager manager = LocationManager.getInstance();
        manager.setLocations(new LinkedList<Location>());
        assertEquals(manager.getLocationsName(), sol);
    }

    static Stream<Arguments> getName() {

        LinkedList<Location> list= new LinkedList<Location>();
        addAll(list, new Location("Valencia"), new Location("Madrid"), new Location("Pekin"));
        ArrayList<String> sol = new ArrayList<String>();
        addAll(sol, "Valencia","Madrid","Pekin");
        LinkedList<Location> list2= new LinkedList<Location>();
        addAll(list2, new Location("Valencia"), new Location("Castellon"), new Location("Pekin"));
        ArrayList<String> sol2 = new ArrayList<String>();
        addAll(sol2, "Valencia","Castellon","Pekin");
        return Stream.of(
                Arguments.of(list, sol),
                Arguments.of(list2, sol2)
        );
    }

    @Test
    void getLocationsNameInvalidCase() {
        LocationManager manager = LocationManager.getInstance();
        manager.setLocations(new LinkedList<Location>());
        assertEquals(manager.getLocationsName(), new LinkedList<Location>());
    }

}