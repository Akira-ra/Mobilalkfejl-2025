package com.example.furniturewebshop;

import java.util.ArrayList;

public class BasketManager {
    private static BasketManager instance;
    private ArrayList<FurnitureItem> basketItems;

    private BasketManager() {
        basketItems = new ArrayList<>();
    }

    public static BasketManager getInstance() {
        if (instance == null) {
            instance = new BasketManager();
        }
        return instance;
    }

    public void addItem(FurnitureItem item) {
        basketItems.add(item);
    }

    public ArrayList<FurnitureItem> getItems() {
        return new ArrayList<>(basketItems);
    }

    public void clearBasket() {
        basketItems.clear();
    }
}

