package com.example.furniturewebshop;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.transition.Slide;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.helper.widget.Grid;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

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
        furnitureItemArrayList = new ArrayList<>();
        furnitureInBasketAdapter = new FurnitureInBasketAdapter(furnitureItemArrayList, this);
        recyclerView.setAdapter(furnitureInBasketAdapter);

        Log.d(LOG_TAG, "Adapter item count: " + furnitureInBasketAdapter.getItemCount());

        initializeData();
    }

    private void initializeData() {
        String[] furnitureNames = getResources().getStringArray(R.array.furnitureItemNames);
        String[] furnitureDetails = getResources().getStringArray(R.array.furnitureItemDetails);
        String[] furniturePrices = getResources().getStringArray(R.array.furnitureItemPrices);
        TypedArray furnitureImage = getResources().obtainTypedArray(R.array.furnitureItemImages);

        furnitureItemArrayList.clear();

        for (int i = 0; i < furnitureNames.length; i++) {
            FurnitureItem item = new FurnitureItem("placeholder", furnitureNames[i], furnitureDetails[i], furniturePrices[i], furnitureImage.getResourceId(i, 0));
            furnitureItemArrayList.add(item);
            Log.d(LOG_TAG, "Items size: " + furnitureItemArrayList.size());
            Log.d(LOG_TAG, "Item added: " + item);
        }

        furnitureImage.recycle();

        furnitureInBasketAdapter.notifyDataSetChanged();

        Log.d(LOG_TAG, "Adapter item count: " + furnitureInBasketAdapter.getItemCount());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        Log.d(LOG_TAG, "onCreateOptionsMenu() meghívva!");
        getMenuInflater().inflate(R.menu.nav_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.searchBar);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
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