package com.example.appkhachhang.Model;

public class DungLuong {
    String _id;
    String boNho;
    Number giaTien;

    public DungLuong() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getBoNho() {
        return boNho;
    }

    public void setBoNho(String boNho) {
        this.boNho = boNho;
    }

    public Number getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(Number giaTien) {
        this.giaTien = giaTien;
    }
}
