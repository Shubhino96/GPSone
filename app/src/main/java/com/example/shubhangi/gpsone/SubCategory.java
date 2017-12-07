package com.example.shubhangi.gpsone;

/**
 * Created by shubhangi on 06-12-2017.
 */

public class SubCategory {
    private String name;
    private int image;

    public SubCategory(String name,int image){
        this.name=name;
        this.image=image;
    }

    public int getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }
}
