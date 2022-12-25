package com.ei10391048.project.modelo;

import com.ei10391048.project.exception.IncorrectLocationException;

public interface LocationApiInterface {
    public Location findLocation() throws IncorrectLocationException;
    public SearchInterface getSearch();
    public SearchInterface setSearch(SearchInterface search);
}
