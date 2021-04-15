package com.example.bizmemo;

public class SaleProduct {
    private String p_id,p_name,sale_id;
    private int p_price,p_qty;

    public SaleProduct(){

    }

    public SaleProduct(String p_id, String p_name, String sale_id, int p_price, int p_qty) {
        this.p_id = p_id;
        this.p_name = p_name;
        this.sale_id = sale_id;
        this.p_price = p_price;
        this.p_qty = p_qty;
    }


    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getSale_id() {
        return sale_id;
    }

    public void setSale_id(String sale_id) {
        this.sale_id = sale_id;
    }

    public int getP_price() {
        return p_price;
    }

    public void setP_price(int p_price) {
        this.p_price = p_price;
    }
}
