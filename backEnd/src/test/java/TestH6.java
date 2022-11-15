import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;


public class TestH6 {
    @Test
    public void darAltaUbicacionPorToponimo(){
// Given
        OpenWather apiUbi = (OpenWather) OpenWather.getInstance();
        GestorUbicaciones ubicaciones = new GestorUbicaciones(apiUbi);
        Toponimo toponimo = new Toponimo("valencia");
// When
        ubicaciones.addPorToponimo(toponimo);
// Then
        assertEquals(1, ubicaciones.getNumetoUbicaciones());
    }

    @Test
    public void darAltaUbicacionPorToponimoInvalido(){
// Given
        OpenWather apiUbi = (OpenWather) OpenWather.getInstance();
        GestorUbicaciones ubicaciones = new GestorUbicaciones(apiUbi);
        Toponimo toponimo = new Toponimo("254213");
// When
        ubicaciones.addPorToponimo(toponimo);
// Then
        assertEquals(0, ubicaciones.getNumetoUbicaciones());
    }

}
