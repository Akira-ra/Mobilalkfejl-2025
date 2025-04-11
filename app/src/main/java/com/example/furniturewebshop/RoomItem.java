package com.example.furniturewebshop;

public class RoomItem {
    private String id;
    private String name;
    private final int image;

    public RoomItem(String id, String name, int image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public int getImage() {
        return image;
    }
}
