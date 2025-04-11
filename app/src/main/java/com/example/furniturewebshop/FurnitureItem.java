package com.example.furniturewebshop;

public class FurnitureItem {
    private String roomId;
    private String name;
    private String details;
    private String price;

    private final int image;

    public FurnitureItem(String roomId, String name, String details, String price, int image) {
        this.roomId = roomId;
        this.name = name;
        this.details = details;
        this.price = price;
        this.image = image;
    }

    public String getRoom() {
        return roomId;
    }

    public String getName() {
        return name;
    }

    public String getDetails() {
        return details;
    }

    public String getPrice() {
        return price;
    }

    public int getImage() {
        return image;
    }
}
