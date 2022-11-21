package com.ei10391048.project.modelo;

public class GeoCodingService implements LocationApiInterface {
    private static GeoCodingService geoCodingService;

    public static GeoCodingService getInstance(){
        if(geoCodingService == null)
            geoCodingService = new GeoCodingService();
        return geoCodingService;
    }


    @Override
    public Location getLocation(String s) {
        return null;
    }
}