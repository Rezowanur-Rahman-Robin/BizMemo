package com.example.bizmemo;

public class Product {
    private String id,name,photo,size,model,price,weight,desc,category;

    public Product(){

    }

    public Product(String id, String name, String photo, String size, String model, String price, String weight, String desc, String category){
        this.id = id;
        this.name = name;
        this.photo = photo;
        this.size = size;
        this.model = model;
        this.price = price;
        this.weight = weight;
        this.desc = desc;
        this.category = category;
    }


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
