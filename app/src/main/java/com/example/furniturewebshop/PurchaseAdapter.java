package com.example.furniturewebshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class PurchaseAdapter extends ArrayAdapter<Purchase> {
    public PurchaseAdapter(Context context, List<Purchase> purchases) {
        super(context, 0, purchases);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.purchase_list_item, parent, false);
        }

        Purchase purchase = getItem(position);

        TextView itemName = convertView.findViewById(R.id.itemName);
        TextView itemQuantity = convertView.findViewById(R.id.itemQuantity);
        TextView itemPrice = convertView.findViewById(R.id.itemPrice);

        itemName.setText("Termék: " + purchase.getItem());
        itemQuantity.setText("Mennyiség: " + purchase.getQuantity());
        itemPrice.setText("Ár: " + purchase.getTotalprice() + " Ft");

        return convertView;
    }
}


