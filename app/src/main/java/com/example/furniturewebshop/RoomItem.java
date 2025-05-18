package com.example.furniturewebshop;

public class RoomItem {
    private String id;
    private String name;
    private String image;

    public RoomItem(){}

    public RoomItem(String id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }
}
