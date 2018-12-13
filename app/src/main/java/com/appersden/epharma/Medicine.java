package com.appersden.epharma;

/**
 * Created by azeR on 12/6/2018.
 */

public class Medicine {
    private String name;
    private String company;
    private String price;

    public Medicine(String name, String company, String price) {
        this.name = name;
        this.company = company;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
