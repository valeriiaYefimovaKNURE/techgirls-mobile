package com.example.techgirls.Models;

public class GridItem {
    String title;
    String caption;
    private int imageID;

    public GridItem(String title, String description, int image) {
        this.title = title;
        this.caption = description;
        this.imageID = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }
}
