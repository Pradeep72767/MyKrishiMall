package com.example.mykrishimall.Model;

public class AdminOrder
{
    private String name, phone, Address, City, Date, Time, totalAmount;

    public AdminOrder()
    {

    }

    public AdminOrder(String name, String phone, String address, String city, String date, String time, String totalAmount) {
        this.name = name;
        this.phone = phone;
        this.Address = address;
        this.City = city;
        this.Date = date;
        this.Time = time;
        this.totalAmount = totalAmount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
}

