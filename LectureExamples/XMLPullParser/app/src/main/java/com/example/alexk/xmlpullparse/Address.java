package com.example.alexk.xmlpullparse;

/**
 * Created by AlexK on 2/17/2018.
 */

public class Address {
    String line1, city, state, zip;

    public Address() {
    }

    @Override
    public String toString() {
        return "Address{" +
                "line1='" + line1 + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zip='" + zip + '\'' +
                '}';
    }
}
