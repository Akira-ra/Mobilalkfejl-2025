package com.example.furniturewebshop;

import com.google.firebase.Timestamp;

public class Purchase {
    private String item;
    private int quantity;
    private long totalprice;
    private Timestamp timestamp;

    public Purchase() {}

    public String getItem() { return item; }
    public int getQuantity() { return quantity; }
    public long getTotalprice() { return totalprice; }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
