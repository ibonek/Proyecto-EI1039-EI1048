package com.ei10391048.project;

import com.ei10391048.project.exception.IncorrectLocationException;
import com.ei10391048.project.modelo.ByName;
import com.ei10391048.project.modelo.GeoCodService;
import com.ei10391048.project.modelo.Location;
import com.ei10391048.project.modelo.LocationManager;
import org.junit.jupiter.api.Test;


import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class GeoCodServiceTest {
    /**
     * Test que comprueba la historia de usuario 6: Como usuario quiero dar de alta una ubicación a partir de un
     * topónimo, con el fin de tenerla disponible en el sistema.
     *
     * @throws IncorrectLocationException
     */
    @Test
    public void addLocationByNameValid() throws IncorrectLocationException {
        GeoCodService geoCodSrv = new GeoCodService();
        String name = "Valencia";
        geoCodSrv.setSearch(new ByName(name));

        LocationManager locations = LocationManager.getInstance();
        int num = locations.getNumberOfLocations();

        locations.setLocationApi(geoCodSrv);
        locations.addByName();

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
}
