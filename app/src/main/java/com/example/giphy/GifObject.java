package com.example.giphy;

public class GifObject {
    String id;
    OriginalObject images;


    public GifObject(String uid, OriginalObject images) {
        this.id = uid;
        this.images = images;
    }

    public String getId() {
        return id;
    }

    public OriginalObject getImages() {
        return images;
    }

    public void setImages(OriginalObject images) {
        this.images = images;
    }
}
