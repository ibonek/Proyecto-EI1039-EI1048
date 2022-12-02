package com.ei10391048.project.modelo;

import com.ei10391048.project.exception.IncorrectLocationException;

public class GeoCodService implements  LocationApiInterface{
    private SearchInterface search;

    public void setSearch(SearchInterface search) {
        this.search = search;
    }

    public Location findLocation() throws IncorrectLocationException {
        return search.search();
    }


    public SearchInterface getSearch() {
        return search;
    }
}