package com.example.wrchain.View.ModalClass;

public class Shops {
    int shopimg;
    String shopname, categoryname;

    public Shops(int shopimg, String shopname, String categoryname) {
        this.shopimg = shopimg;
        this.shopname = shopname;
        this.categoryname = categoryname;
    }

    public int getShopimg() {
        return shopimg;
    }

    public void setShopimg(int shopimg) {
        this.shopimg = shopimg;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }
}
