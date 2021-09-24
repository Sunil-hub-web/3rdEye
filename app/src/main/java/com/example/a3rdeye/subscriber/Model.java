package com.example.a3rdeye.subscriber;

public class Model {

    String id,catagory;

    public Model(String catagory) {
        //this.id = id;
        this.catagory = catagory;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCatagory() {
        return catagory;
    }

    public void setCatagory(String catagory) {
        this.catagory = catagory;
    }
}
