package com.example.giphy;

import java.util.ArrayList;
import java.util.List;

public class ParentObject {
    ArrayList<GifObject> data;

    public ParentObject(ArrayList<GifObject> list) {
        this.data = list;
    }

    public List<GifObject> getList() {
        return data;
    }

    public void setList(ArrayList<GifObject> list) {
        this.data = list;
    }
}
