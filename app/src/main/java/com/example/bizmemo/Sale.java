package com.example.bizmemo;

public class Sale {

    private String c_name,description,time,sale_id;
    private int total_items;

    public Sale(String c_name, String description, String time, String sale_id, int total_items){
        this.c_name = c_name;
        this.description = description;
        this.time = time;
        this.sale_id = sale_id;
        this.total_items = total_items;
    }


    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSale_id() {
        return sale_id;
    }

    public void setSale_id(String sale_id) {
        this.sale_id = sale_id;
    }
}
