package com.appersden.epharma;

/**
 * Created by azeR on 12/8/2018.
 */

public class Cart {

    private String name;
    private String company;
    private int power;
    private int first;
    private int totalmultiply;
    private double price;

    public Cart(String name, String company, int power, int first, int totalmultiply, double price) {
        this.name = name;
        this.company = company;
        this.power = power;
        this.first = first;
        this.totalmultiply = totalmultiply;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public int getTotalmultiply() {
        return totalmultiply;
    }

    public void setTotalmultiply(int totalmultiply) {
        this.totalmultiply = totalmultiply;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
