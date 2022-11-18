package com.ei10391048.project;

import com.ei10391048.project.exception.IncorrectLocationException;
import com.ei10391048.project.modelo.GeoCodingService;
import com.ei10391048.project.modelo.Location;
import com.ei10391048.project.modelo.LocationManager;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class GeoCodingServiceTest {
    /**
     * Test que comprueba la historia de usuario 6: Como usuario quiero dar de alta una ubicación a partir de un
     * topónimo, con el fin de tenerla disponible en el sistema.
     *
     * @throws IncorrectLocationException
     */
    @Test
    public void addLocationByNameValid() throws IncorrectLocationException {
        GeoCodingService geoCodSrv = GeoCodingService.getInstance();
        LocationManager locations = new LocationManager(geoCodSrv);
        String name = "Valencia";
        locations.addByName(name);
        assertEquals(1, locations.getNumberOfLocations());
    }

    @Test(expectedExceptions = IncorrectLocationException.class)
    public void addLocationByNameInvalid() throws IncorrectLocationException {
        GeoCodingService geoCodSrv = GeoCodingService.getInstance();
        LocationManager locations = new LocationManager(geoCodSrv);
        String name = "Balencia";
        locations.addByName(name);
    }

    /**
     * Test que comprueba la historia de usuario 7: Como usuario quiero dar de alta una ubicación a partir de unas
     * coordenadas geográficas, con el fin de tenerla disponible en el sistema.
     *
     * @throws IncorrectLocationException
     */
    @Test
    public void addLocationByCoordinatesValid() throws IncorrectLocationException {
        GeoCodingService geoCodSrv = GeoCodingService.getInstance();
        LocationManager locations = new LocationManager(geoCodSrv);
        Location location = new Location(39.46975, -0.37739);
        locations.addByCoordinates(location);
        assertEquals(1, locations.getNumberOfLocations());
    }

    @Test(expectedExceptions = IncorrectLocationException.class)
    public void addLocationByCoordinatesInvalid() throws IncorrectLocationException {
        GeoCodingService geoCodSrv = GeoCodingService.getInstance();
        LocationManager locations = new LocationManager(geoCodSrv);
        Location location = new Location(39.46975, -0.37739);
        locations.addByCoordinates(location);
        locations.addByCoordinates(location);
    }
}
