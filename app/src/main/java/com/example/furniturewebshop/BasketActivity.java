package com.example.furniturewebshop;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Slide;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BasketActivity extends AppCompatActivity {
    private static final String LOG_TAG = BasketActivity.class.getName();
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;

    private RecyclerView recyclerView;
    private ArrayList<FurnitureItem> furnitureItemArrayList;
    private FurnitureInBasketAdapter furnitureInBasketAdapter;
    private int gridNumber = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "onCreate() meghívva!");
        EdgeToEdge.enable(this);
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        getWindow().setExitTransition(new Slide());
        setContentView(R.layout.activity_basket);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            Log.d(LOG_TAG, "Autentikált felhasználó");
        } else {
            Log.d(LOG_TAG, "Nem autentikált felhasználó");
            finish();
        }

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, gridNumber));

        furnitureItemArrayList = new ArrayList<>(BasketManager.getInstance().getItems());
        furnitureInBasketAdapter = new FurnitureInBasketAdapter(furnitureItemArrayList, BasketActivity.this);
        recyclerView.setAdapter(furnitureInBasketAdapter);

        TextView emptyView = findViewById(R.id.emptyView);
        if (furnitureItemArrayList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }

        Button btnPlaceOrder = findViewById(R.id.btnPlaceOrder);
        btnPlaceOrder.setOnClickListener(v -> {
            if (furnitureItemArrayList.isEmpty()) {
                Toast.makeText(BasketActivity.this, "A kosarad üres!", Toast.LENGTH_SHORT).show();
                return;
            }

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            String userId = firebaseUser.getUid();

            for (FurnitureItem item : furnitureItemArrayList) {
                Map<String, Object> order = new HashMap<>();
                order.put("item", item.getName());
                order.put("quantity", 1);
                order.put("totalprice", item.getPrice());
                order.put("userid", userId);
                order.put("timestamp", new Timestamp(new Date()));

                db.collection("Purchases")
                        .add(order)
                        .addOnSuccessListener(documentReference -> Log.d(LOG_TAG, "Rendelés mentve: " + documentReference.getId()))
                        .addOnFailureListener(e -> Log.w(LOG_TAG, "Hiba a mentéskor", e));
            }

            Toast.makeText(BasketActivity.this, "Rendelések sikeresen leadva!", Toast.LENGTH_LONG).show();

            BasketManager.getInstance().clearBasket();
            furnitureItemArrayList.clear();
            furnitureInBasketAdapter.notifyDataSetChanged();

            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        });

        Log.d(LOG_TAG, "Adapter item count: " + furnitureInBasketAdapter.getItemCount());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        Log.d(LOG_TAG, "onCreateOptionsMenu() meghívva!");
        getMenuInflater().inflate(R.menu.nav_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.navLogout) {
            Log.d(LOG_TAG, "Logout clicked!");
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
            finish();
            return true;
        } else if (id == R.id.navSettings) {
            Log.d(LOG_TAG, "Setting clicked!");
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
            finish();
            //TODO
            return true;
        } else if (id == R.id.navBasket) {
            Log.d(LOG_TAG, "Basket clicked!");
            return true;
        } else if (id == R.id.navProfile) {
            Log.d(LOG_TAG, "Profile clicked!");
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
            return true;
        } else if (id == R.id.navRooms) {
            Log.d(LOG_TAG, "Rooms clicked!");
            Intent intent = new Intent(this, RoomListActivity.class);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }
}