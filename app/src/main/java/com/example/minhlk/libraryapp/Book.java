package com.example.minhlk.myapplication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Book implements Serializable {
    String Name,Description,Image,idAuthor,id,authorName;
    List<BookGenre> bookGenre;

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
    public void setBookGenreIds(List<String> bookGenreIds){
        this.bookGenre = new ArrayList<>();
        for(String id : bookGenreIds)
            bookGenre.add(new BookGenre(id));
    }
    public List<String> getBookGenreIds(){

            List<String> temp = new ArrayList<>();
            for(BookGenre b : bookGenre)
                temp.add(b.getIdGenre());
        return temp;
    }
    public List<BookGenre> getBookGenre() {
        return bookGenre;
    }

    public void setBookGenre(List<BookGenre> bookGenre) {
        this.bookGenre = bookGenre;
    }

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
