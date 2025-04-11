package com.example.furniturewebshop;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class FurnitureItemAdapter extends RecyclerView.Adapter<FurnitureItemAdapter.ViewHolder> implements Filterable {
    private ArrayList<FurnitureItem> furnitureItemsData;
    private ArrayList<FurnitureItem> allFurnitureItemsData;
    private Context context;
    private int lastPosition = -1;

    public FurnitureItemAdapter(ArrayList<FurnitureItem> furnitureItemsData, Context context) {
        this.furnitureItemsData = furnitureItemsData;
        this.allFurnitureItemsData = furnitureItemsData;
        this.context = context;
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    @NonNull
    @Override
    public FurnitureItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FurnitureItemAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.furniture_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FurnitureItemAdapter.ViewHolder holder, int position) {
        FurnitureItem currentItem = furnitureItemsData.get(position);
        holder.bindTo(currentItem);
    }

    @Override
    public int getItemCount() {
        return furnitureItemsData.size();
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

            itemView.findViewById(R.id.addToBasket).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO
                    Log.d("FurnitureItemAdapter", "Add to basket button clicked!");
                }
            });
        }

        public void bindTo(FurnitureItem currentItem) {
            this.furnitureName.setText(currentItem.getName());
            this.furnitureDetails.setText(currentItem.getDetails());
            this.furniturePrice.setText(currentItem.getPrice());
            Glide.with(context).load(currentItem.getImage()).into(furnitureImage);
        }
    }
}
