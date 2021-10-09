package com.example.ameacasambientaisfirebase;

import androidx.annotation.NonNull;
import java.io.Serializable;

public class Threat implements Serializable {

    private String address;
    private String date;
    private String description;
    private String image;

    public Threat(){

    }

    public String getAddress(){
        return address;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public String getDate(){
        return date;
    }

    public void setDate(String date){
        this.date = date;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getImage(){
        return image;
    }

    public void setImage(String image){
        this.image = image;
    }

    @NonNull
    @Override
    public String toString(){
        return address + " " + date + " " + description;
    }

}
