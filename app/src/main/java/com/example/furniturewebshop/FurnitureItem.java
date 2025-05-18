package com.example.furniturewebshop;

public class FurnitureItem {
    private String roomId;
    private String name;
    private String details;
    private long price;

    private String image;

    public FurnitureItem(){}

    public FurnitureItem(String roomId, String name, String details, long price, String image) {
        this.roomId = roomId;
        this.name = name;
        this.details = details;
        this.price = price;
        this.image = image;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getName() {
        return name;
    }

    public String getDetails() {
        return details;
    }

    public long getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }
}
