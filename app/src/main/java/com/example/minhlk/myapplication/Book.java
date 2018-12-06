package com.example.minhlk.myapplication;

import java.io.Serializable;

public class Book implements Serializable {
    String Name,Description,Image,idAuthor,id;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getIdAuthor() {
        return idAuthor;
    }

    public void setIdAuthor(String idAuthor) {
        this.idAuthor = idAuthor;
    }

    @Override
    public String toString() {
        return "Book{" +
                "Name='" + Name + '\'' +
                ", Description='" + Description + '\'' +
                ", Image='" + Image + '\'' +
                ", idAuthor='" + idAuthor + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
