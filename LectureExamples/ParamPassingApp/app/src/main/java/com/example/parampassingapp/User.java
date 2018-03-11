package com.example.parampassingapp;

import java.io.Serializable;

/**
 * Created by alexb on 1/27/2018.
 */

public class User implements Serializable {
    String name;
    double age;

    public User(String name, double age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
