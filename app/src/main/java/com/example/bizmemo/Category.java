package com.example.bizmemo;

public class Category {
    private String cat_name;

    public Category(){

    }

    public Category(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }
}
