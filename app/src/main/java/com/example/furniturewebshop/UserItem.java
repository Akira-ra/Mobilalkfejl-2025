package com.example.furniturewebshop;

public class UserItem {
    private String name;
    private String email;
    private String city;
    private String postNr;
    private String street;
    private int numberOfOrders;

    public UserItem() {
    }

    public UserItem(String name, String email, String city, String postNr, String street) {
        this.name = name;
        this.email = email;
        this.city = city;
        this.postNr = postNr;
        this.street = street;
        this.numberOfOrders = 0;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getCity() {
        return city;
    }

    public String getPostNr() {
        return postNr;
    }

    public String getStreet() {
        return street;
    }

    public int getNumberOfOrders() {
        return numberOfOrders;
    }
}
