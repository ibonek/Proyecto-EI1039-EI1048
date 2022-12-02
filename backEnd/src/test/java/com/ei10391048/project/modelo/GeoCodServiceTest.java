package com.ei10391048.project.modelo;

import com.ei10391048.project.exception.IncorrectLocationException;
import com.ei10391048.project.modelo.ByName;
import com.ei10391048.project.modelo.GeoCodService;
import com.ei10391048.project.modelo.Location;
import com.ei10391048.project.modelo.LocationManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class GeoCodServiceTest {


    /*
/**
 * Test que comprueba la historia de usuario 6: Como usuario quiero dar de alta una ubicación a partir de un
 * topónimo, con el fin de tenerla disponible en el sistema.
 *
 * @throws IncorrectLocationException

    @Test
    public void addLocationByNameValid() throws IncorrectLocationException {
        LocationManager locations = LocationManager.getInstance();
        int actual = locations.getNumberOfLocations();
        String name = "Valencia";
        locations.addByName(name);
        assertEquals(actual, actual+1);
    }

    @Test(expectedExceptions = IncorrectLocationException.class)
    public void addLocationByNameInvalid() throws IncorrectLocationException {
        LocationManager locations = LocationManager.getInstance();
        String name = "Balencia";
        locations.addByName(name);
    }


    /**
     * Test que comprueba la historia de usuario 7: Como usuario quiero dar de alta una ubicación a partir de unas
     * coordenadas geográficas, con el fin de tenerla disponible en el sistema.
     *
     * @throws IncorrectLocationException

    @Test
    public void addLocationByCoordinatesValid() throws IncorrectLocationException {
        GeoCodService geoCodSrv = GeoCodService.getInstance();
        LocationManager locations = new LocationManager(geoCodSrv);
        Location location = new Location(39.46975, -0.37739);
        locations.addByCoordinates(location);
        assertEquals(1, locations.getNumberOfLocations());
    }

    @Test(expectedExceptions = IncorrectLocationException.class)
    public void addLocationByCoordinatesInvalid() throws IncorrectLocationException {
        GeoCodService geoCodSrv = GeoCodService.getInstance();
        LocationManager locations = new LocationManager(geoCodSrv);
        Location location = new Location(39.46975, -0.37739);
        locations.addByCoordinates(location);
        locations.addByCoordinates(location);
    }
*/
    /**
     * Test que comprueba la historia de usuario 8: Como usuario quiero validar el topónimo de una ubicación disponible
     * en los servicios API activos, con el fin de evaluar su utilidad.
     *
     * @throws IncorrectLocationException
     */

    @Test
    public void validarToponimoValido() throws IncorrectLocationException {
            String toponimo = "Valencia";
            GeoCodService geoCodSrv = new GeoCodService();
            geoCodSrv.setSearch(new ByName(toponimo));
            Location location = geoCodSrv.findLocation();
            assertEquals(location.getName(), toponimo);
    }

    @Test
    void validarToponimoInvalido(){
        try {
            String toponimo = "Balencia";
            GeoCodService geoCodSrv = new GeoCodService();
            geoCodSrv.setSearch(new ByName(toponimo));
            Location localizacion = geoCodSrv.findLocation();
        } catch (IncorrectLocationException ex){
            assertTrue(true);
        }
    }

}
