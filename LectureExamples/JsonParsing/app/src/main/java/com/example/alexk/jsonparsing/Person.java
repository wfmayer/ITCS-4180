package com.example.alexk.jsonparsing;

/**
 * Created by AlexK on 2/17/2018.
 */

public class Person {
    String name;
    long id;
            int age;
    Address address;

    public Person() {
    }

//    public Person(String name, int id, int age, Address address) {
//        this.name = name;
//        this.id = id;
//        this.age = age;
//        this.address = address;
//    }


    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", age=" + age +
                ", address=" + address +
                '}';
    }
}
