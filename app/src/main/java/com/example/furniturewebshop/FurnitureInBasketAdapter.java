package com.example.furniturewebshop;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class FurnitureInBasketAdapter extends RecyclerView.Adapter<FurnitureInBasketAdapter.ViewHolder>{
    private ArrayList<FurnitureItem> furnitureItemsInBasket;
    private Context context;
    private int lastPosition = -1;

    public FurnitureInBasketAdapter(ArrayList<FurnitureItem> furnitureItemsInBasket, Context context) {
        this.furnitureItemsInBasket = furnitureItemsInBasket;
        this.context = context;
    }

    @NonNull
    @Override
    public FurnitureInBasketAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.furniture_item_in_basket, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FurnitureInBasketAdapter.ViewHolder holder, int position) {
        FurnitureItem currentItem = furnitureItemsInBasket.get(position);
        holder.bindTo(currentItem);
    }

    @Override
    public int getItemCount() {
        return furnitureItemsInBasket.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView furnitureName;
        private TextView furnitureDetails;
        private TextView furniturePrice;
        private ImageView furnitureImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.furnitureName = itemView.findViewById(R.id.furnitureName);
            this.furnitureDetails = itemView.findViewById(R.id.furnitureDetails);
            this.furniturePrice = itemView.findViewById(R.id.furniturePrice);
            this.furnitureImage = itemView.findViewById(R.id.furnitureImage);
        }

        public void bindTo(FurnitureItem currentItem) {
            this.furnitureName.setText(currentItem.getName());
            this.furnitureDetails.setText(currentItem.getDetails());
            this.furniturePrice.setText(currentItem.getPrice());
            Glide.with(context).load(currentItem.getImage()).into(furnitureImage);
        }
    }
}
