package com.example.wrchain.View.ModalClass;

public class HorizontalCategoryProduct {
    private int Categoryimg;
    private String Categoryname;

    public HorizontalCategoryProduct(int categoryimg, String categoryname) {
        Categoryimg = categoryimg;
        Categoryname = categoryname;
    }

    public int getCategoryimg() {
        return Categoryimg;
    }

    public void setCategoryimg(int categoryimg) {
        Categoryimg = categoryimg;
    }

    public String getCategoryname() {
        return Categoryname;
    }

    public void setCategoryname(String categoryname) {
        Categoryname = categoryname;
    }
}
