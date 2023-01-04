package com.ei10391048.project.modelo;

import com.ei10391048.project.exception.IncorrectAliasException;
import com.ei10391048.project.exception.IncorrectLocationException;
import com.ei10391048.project.exception.NotSavedException;
import com.ei10391048.project.modelo.user.User;
import com.ei10391048.project.modelo.user.UserFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.stream.Stream;

import static java.util.Collections.addAll;
import static org.junit.jupiter.api.Assertions.*;

public class LocationTest {
    Location location;
    @BeforeEach
    public void setUp(){
        location = new Location();
    }

    /**
     * Test que comprueba la historia de usuario 11: Como usuario quiero asignar un alias a una ubicación, con el fin de
     * asignar nombres personalizados a las ubicaciones. Escenario 1: El alias es correcto.
     */
    @Test
    void getLocationsAliasValidCase() throws IncorrectAliasException {
        String name = "Valencia";
        String alias = "casa";
        location.setName(name);
        assertEquals(location.getAlias(), location.getName());
        location.setAlias(alias);
        assertEquals(alias, location.getAlias());
    }


    /**
     * Test que comprueba la historia de usuario 11: Como usuario quiero asignar un alias a una ubicación, con el fin de
     * asignar nombres personalizados a las ubicaciones. Escenario 2: El alias es incorrecto.
     */
    @Test
    void getLocationsAliasInvalidCase(){
        try {
            String name = "Valencia";
            String alias = "";
            location.setName(name);
            location.setAlias(alias);
            fail();
        } catch (IncorrectAliasException e){
            assertTrue(true);
        }
    }



}
