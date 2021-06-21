package com.example.movify;

public class CategoryItem {

    int ID;
    String movieNames;
    String imageURL;
    String FileURL;

    public CategoryItem(int ID, String movieNames, String imageURL, String fileURL) {
        this.ID = ID;
        this.movieNames = movieNames;
        this.imageURL = imageURL;
        FileURL = fileURL;
    }


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getMovieNames() {
        return movieNames;
    }

    public void setMovieNames(String movieNames) {
        this.movieNames = movieNames;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getFileURL() {
        return FileURL;
    }

    public void setFileURL(String fileURL) {
        FileURL = fileURL;
    }
}
