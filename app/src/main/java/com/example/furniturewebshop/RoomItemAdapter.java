package com.example.furniturewebshop;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RoomItemAdapter extends RecyclerView.Adapter<RoomItemAdapter.ViewHolder>{
    private ArrayList<RoomItem> roomItemsData;
    private ArrayList<RoomItem> allRoomItemsData;
    private Context context;
    private int lastPosition = -1;

    RoomItemAdapter(Context context, ArrayList<RoomItem> roomData) {
        this.roomItemsData = roomData;
        this.allRoomItemsData = roomData;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.room_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RoomItemAdapter.ViewHolder holder, int position) {
        RoomItem currentItem = roomItemsData.get(position);
        holder.bindTo(currentItem);

        holder.browseFurnitureButton.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, FurnitureByRoomActivity.class);
            intent.putExtra("roomId", currentItem.getId());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return roomItemsData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView roomName;
        private ImageView roomImage;
        public Button browseFurnitureButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.roomName = itemView.findViewById(R.id.roomName);
            this.roomImage = itemView.findViewById(R.id.roomImage);
            browseFurnitureButton = itemView.findViewById(R.id.browseFurnitureByRoom);

            itemView.findViewById(R.id.browseFurnitureByRoom).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO
                    Log.d("RoomItemAdapter", "Browse furniture by room button clicked");
                }
            });
        }

        public void bindTo(RoomItem currentItem) {
            this.roomName.setText(currentItem.getName());

            String imageName = currentItem.getImage().replace(".jpg", "");
            int imageResId;
            imageResId = context.getResources().getIdentifier(
                    imageName, "drawable", context.getPackageName());
            Log.d("RoomItemAdapter", "Image name: " + imageName + ", ResID: " + imageResId);
            roomImage.setImageResource(imageResId);
        }
    }
}
