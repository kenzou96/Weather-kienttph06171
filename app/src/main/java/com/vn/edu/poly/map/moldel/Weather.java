package com.vn.edu.poly.map.moldel;

public class Weather {
    public String trangThai,ngay,minND,maxND,icon;

    public Weather(String ngay, String trangThai, String icon, String minND, String maxND) {
        this.trangThai = trangThai;
        this.ngay = ngay;
        this.minND = minND;
        this.maxND = maxND;
        this.icon = icon;
    }
}
