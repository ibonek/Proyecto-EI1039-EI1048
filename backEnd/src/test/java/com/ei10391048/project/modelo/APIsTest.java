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
    @Test
    public void getInfo_API(){
        WeatherInformation info = new WeatherInformation();
        info.setWeatherState("Cloudy");
        list.add(info);

        when(apiMock.generateInfo(any(String.class))).thenReturn(list);
        APIManager manager = new APIManager();
        manager.addAPI(apiMock);
        assertEquals(manager.getInfo("Valencia").get(0), list);

    }
}
