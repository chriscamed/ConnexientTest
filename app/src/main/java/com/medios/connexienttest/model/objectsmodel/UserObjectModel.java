package com.medios.connexienttest.model.objectsmodel;

import java.io.Serializable;

/**
 * Created by Camilo on 7/15/17.
 */

public class UserObjectModel implements Serializable {

    private String name;
    private String lastName;
    private String imageUrl;
    private char gender;
    private LocationModel location;

    public UserObjectModel(String name, String lastName, String imageUrl, char gender, LocationModel location) {
        this.name = name;
        this.lastName = lastName;
        this.imageUrl = imageUrl;
        this.gender = gender;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public LocationModel getLocation() {
        return location;
    }

    public void setLocation(LocationModel location) {
        this.location = location;
    }
}
