package com.example.minhlk.myapplication;

public class Genre implements Comparable<Genre>{
    public String id,name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Genre(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Genre() {
    }

    @Override
    public int compareTo(Genre o) {
        return this.name.toLowerCase().compareTo(o.getName().toLowerCase());
    }
}
