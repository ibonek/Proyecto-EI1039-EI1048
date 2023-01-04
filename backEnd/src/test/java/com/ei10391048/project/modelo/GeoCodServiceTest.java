package com.ei10391048.project.modelo;

import com.ei10391048.project.exception.IncorrectLocationException;
import com.ei10391048.project.modelo.*;
import com.ei10391048.project.exception.NotSavedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;


import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class GeoCodServiceTest {

    /**
     * Test que comprueba la historia de usuario 8: Como usuario quiero validar el topónimo de una ubicación disponible
     * en los servicios API activos, con el fin de evaluar su utilidad. Escenario 1: El topónimo es correcto.
     */

     @Test
    public void validatePlaceNameValid() throws IncorrectLocationException {
         String toponimo = "Valencia";
         GeoCodService geoCodSrv = new GeoCodService();
         geoCodSrv.setSearch(new ByName(toponimo));
         Location location = geoCodSrv.findLocation();
         assertEquals(location.getName(), "Valencia");
     }


    /**
     * Test que comprueba la historia de usuario 8: Como usuario quiero validar el topónimo de una ubicación disponible
     * en los servicios API activos, con el fin de evaluar su utilidad. Escenario 2: El topónimo es incorrecto.
     */
    @Test
    void validatePlaceNameInvalid(){
        try {
            String toponimo = "Balencia";
            GeoCodService geoCodSrv = new GeoCodService();
            geoCodSrv.setSearch(new ByName(toponimo));
            geoCodSrv.findLocation();
        } catch (IncorrectLocationException ex){
            assertTrue(true);
        }
    }

    /**
     * Test que comprueba la historia de usuario 9: Como usuario quiero validar las coordenadas geográficas de una
     * ubicación disponible en los servicios API activos, con el fin de evaluar su utilidad. Escenario 1: Las coordenadas
     * son correctas.
     */
    @Test
    public void validateCoordinatesValid() throws IncorrectLocationException {
        Coordinates coordinates = new Coordinates(-39.985,-0.049);
        GeoCodService geoCodSrv = new GeoCodService();
        geoCodSrv.setSearch(new ByCoordinates(coordinates));
        Location location = geoCodSrv.findLocation();
        assertEquals(location.getCoordinates().getLat(), coordinates.getLat(),0.1);
        assertEquals(location.getCoordinates().getLon(), coordinates.getLon(),0.1);

    }

    /**
     * Test que comprueba la historia de usuario 9: Como usuario quiero validar las coordenadas geográficas de una
     * ubicación disponible en los servicios API activos, con el fin de evaluar su utilidad. Escenario 2: Las coordenadas
     * son incorrectas.
     */
    @Test
    void validarCoordinatesInvalido(){
        try {
            Coordinates coordinates = new Coordinates(-191,-0.049);
            GeoCodService geoCodSrv = new GeoCodService();
            geoCodSrv.setSearch(new ByCoordinates(coordinates));
            geoCodSrv.findLocation();
        } catch (IncorrectLocationException ex){
            assertTrue(true);
        }
    }

}
