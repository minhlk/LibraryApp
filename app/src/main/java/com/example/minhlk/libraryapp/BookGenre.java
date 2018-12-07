package com.example.minhlk.myapplication;

import java.io.Serializable;

public class BookGenre implements Serializable {
    String idGenre;

    public String getIdGenre() {
        return idGenre;
    }

    public void setIdGenre(String idGenre) {
        this.idGenre = idGenre;
    }

    public BookGenre(String idGenre) {
        this.idGenre = idGenre;
    }

}