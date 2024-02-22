package com.rohith.ecommercemobilefashionapp;

import java.util.HashMap;
import java.util.Map;

public class Address {
    String street;
    String city;
    String state;
    String country;
    String zipCode;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Address(String street, String city, String state, String country, String zipCode) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipCode = zipCode;
    }

    public Address() {
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

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> addressMap = new HashMap<>();
        addressMap.put("street", street);
        addressMap.put("city",city);
        addressMap.put("state",state);
        addressMap.put("country", country);
        addressMap.put("zipcode",zipCode);
        return addressMap;
    }

}
