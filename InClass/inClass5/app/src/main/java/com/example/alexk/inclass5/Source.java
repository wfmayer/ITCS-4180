package com.example.alexk.inclass5;

/**
 * Created by AlexK on 2/19/2018.
 */

public class Source {
    String name, id;

    public Source(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public Source() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name;
    }
}
