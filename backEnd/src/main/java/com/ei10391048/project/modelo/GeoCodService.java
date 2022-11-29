package com.ei10391048.project.modelo;

public class GeoCodService implements  LocationApiInterface{
    private SearchInterface search;

    public void setSearch(SearchInterface search) {
        this.search = search;
    }

    public Location findLocation(){
        return search.search();
    }
}