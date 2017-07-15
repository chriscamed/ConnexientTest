package com.medios.connexienttest.model.objectsmodel;

import java.io.Serializable;

/**
 * Created by Camilo on 7/15/17.
 */

public class LocationModel implements Serializable {

    private String street;
    private String city;
    private String state;

    public LocationModel(String street, String city, String state) {
        this.street = street;
        this.city = city;
        this.state = state;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
