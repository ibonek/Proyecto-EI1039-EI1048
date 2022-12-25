package com.ei10391048.project.modelo;

import com.ei10391048.project.exception.IncorrectLocationException;

public class GeoCodService implements  LocationApiInterface{

    private SearchInterface search;

    public SearchInterface setSearch(SearchInterface search) {
        this.search = search;
        return search;
    }

    public Location findLocation() throws IncorrectLocationException {
        System.out.println("aa");
        return search.search();
    }


    public SearchInterface getSearch() {
        return search;
    }


}