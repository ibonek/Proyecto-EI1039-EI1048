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
     * Test que comprueba la historia de usuario 6: Como usuario quiero dar de alta una ubicación a partir de un
     * topónimo, con el fin de tenerla disponible en el sistema.
     *
     * @throws IncorrectLocationException
     */
    @Test
    public void addLocationByNameValid() throws IncorrectLocationException, NotSavedException {
        GeoCodService geoCodSrv = new GeoCodService();
        String name = "Valencia";
        geoCodSrv.setSearch(new ByName(name));

        LocationManager locations = LocationManager.getInstance();
        int num = locations.getNumberOfLocations();

        locations.setLocationApi(geoCodSrv);
        locations.addLocation();

        assertEquals( locations.getNumberOfLocations(), num + 1);
    }


    @Test
    public void addLocationByNameInvalid() throws IncorrectLocationException {
        try {
            String toponimo = "";
            GeoCodService geoCodSrv = new GeoCodService();
            geoCodSrv.setSearch(new ByName(toponimo));
            Location localizacion = geoCodSrv.findLocation();
        } catch (IncorrectLocationException ex){
            assertTrue(true);
        }
}


    /**
     * Test que comprueba la historia de usuario 7: Como usuario quiero dar de alta una ubicación a partir de unas
     * coordenadas geográficas, con el fin de tenerla disponible en el sistema.
     *
     * @throws IncorrectLocationException
    */
     @Test
     public void addLocationByCoordinatesValid() throws IncorrectLocationException, NotSavedException {
         Coordinates coordinates = new Coordinates(-0.3773900,39.4697500);
         GeoCodService geoCodSrv = new GeoCodService();
         geoCodSrv.setSearch(new ByCoordinates(coordinates));
         LocationManager locations = LocationManager.getInstance();
         locations.setLocationApi(geoCodSrv);
         int num = locations.getNumberOfLocations();
         locations.addLocation();
         assertEquals(locations.getNumberOfLocations(), num + 1);
     }


     @ParameterizedTest
     @MethodSource("coords")
     public void addLocationByCoordinatesInvalid(double lat, double lon){
         try {
             Coordinates coordinates = new Coordinates(lat,lon);
             GeoCodService geoCodSrv = new GeoCodService();
             geoCodSrv.setSearch(new ByCoordinates(coordinates));
             Location localizacion = geoCodSrv.findLocation();
         } catch (IncorrectLocationException ex){
             assertTrue(true);
         }
     }

     static Stream<Arguments> coords(){
         return Stream.of(
                 Arguments.of(-190,100),
                 Arguments.of(190,100),
                 Arguments.of(100,190),
                 Arguments.of(100,-190)
         );
     }

    /**
     * Test que comprueba la historia de usuario 8: Como usuario quiero validar el topónimo de una ubicación disponible en los servicios API activos, con el fin de evaluar su utilidad.
     *
     * @throws IncorrectLocationException
     */

     @Test
    public void validatePlaceNameValid() throws IncorrectLocationException {
         String toponimo = "Valencia";
         GeoCodService geoCodSrv = new GeoCodService();
         geoCodSrv.setSearch(new ByName(toponimo));
         Location location = geoCodSrv.findLocation();
         assertEquals(location.getName(), "Valencia");
     }

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
     * Test que comprueba la historia de usuario 9: Como usuario quiero validar las coordenadas geográficas de una ubicación disponible en los servicios API activos, con el fin de evaluar su utilidad.
     *
     * @throws IncorrectLocationException
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

    @Test
    void validarToponimoInvalido(){
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
