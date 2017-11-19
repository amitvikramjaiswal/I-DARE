package com.opensource.app.idare.utils;

/**
 * Created by amitvikramjaiswal on 19/11/17.
 */

public enum SearchPlace {

    SHOPPING_MALL("shopping_mall"),
    POLICE_STATION("police"),
    CAFE("cafe");

    private String value;

    SearchPlace(String searchPlace) {
        this.value = searchPlace;
    }

    public String getValue() {
        return value;
    }
}
