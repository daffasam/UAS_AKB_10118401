package com.example.uas_akb_10118401;

public class MainModel {
    private String txtName, color, urutan;
    private int imgSrc;

    public MainModel(String urutan, String txtName, int imgSrc) {
        this.txtName = txtName;
        this.urutan = urutan;
        this.imgSrc = imgSrc;
        this.color = color;
    }

    public String getUrutan() {
        return urutan;
    }

    public void setUrutan(String urutan) {
        this.urutan = urutan;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTxtName() {
        return txtName;
    }

    public void setTxtName(String txtName) {
        this.txtName = txtName;
    }

    public int getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(int imgSrc) {
        this.imgSrc = imgSrc;
    }
}

