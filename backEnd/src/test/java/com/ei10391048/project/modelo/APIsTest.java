package com.ei10391048.project.modelo;

import com.ei10391048.project.modelo.api.API;
import com.ei10391048.project.modelo.api.OpenWeather;
import com.ei10391048.project.modelo.api.TicketMaster;
import com.ei10391048.project.modelo.information.APIInformation;
import com.ei10391048.project.modelo.information.WeatherInformation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.LinkedList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class APIsTest {

    private API apiMock;
    List<APIInformation> list;

    @BeforeEach
    public void setUp(){
        list = new LinkedList<>();
        apiMock = Mockito.mock(API.class);
    }

    /**
     * Test de integración que comprueba la historia de usuario 18: Como usuario
     * quiero consultar fácilmente la información de cualquiera de las ubicaciones
     * activas por separado.
     */
    @Test
    public void getInfoAPIValidCase(){
        WeatherInformation info = new WeatherInformation();
        info.setWeatherState("Cloudy");
        list.add(info);

        List<API> apiList = new LinkedList<>();

        when(apiMock.generateInfo(any(String.class))).thenReturn(list);
        apiList.add(apiMock);
        APIManager manager = new APIManager();
        manager.setApiList(apiList);
        manager.generateInfo("Valencia");
        assertEquals(manager.getWeatherInformation(), list);

    }
}
