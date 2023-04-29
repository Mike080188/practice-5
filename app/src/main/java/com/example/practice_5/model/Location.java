package com.example.practice_5.model;

import java.io.Serializable;

public class Location implements Serializable {
    private String name;
    private String address;
    public Location(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Location() {
    }
}
