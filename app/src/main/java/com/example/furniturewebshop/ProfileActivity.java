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

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    private static final String LOG_TAG = ProfileActivity.class.getName();
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    private Button deleteProfileButton, editProfileDetails;;
    private FirebaseFirestore db;
    private EditText profileName, profileCity, profilePostNr, profileStreet;
    private TextView profileEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        getWindow().setExitTransition(new Slide());
        setContentView(R.layout.activity_profile);
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

        db = FirebaseFirestore.getInstance();

        profileName = findViewById(R.id.profileName);
        profileEmail = findViewById(R.id.profileEmail);
        profileCity = findViewById(R.id.profileCity);
        profilePostNr = findViewById(R.id.profilePostNr);
        profileStreet = findViewById(R.id.profileStreet);
        editProfileDetails = findViewById(R.id.editProfileDetails);

        editProfileDetails.setOnClickListener(view -> updateProfileData());

        deleteProfileButton = findViewById(R.id.deleteProfile);
        deleteProfileButton.setOnClickListener(v -> deleteProfile());

        Button pastOrdersButton = findViewById(R.id.pastOrders);
        pastOrdersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, PurchaseHistoryActivity.class);
                startActivity(intent);
            }
        });
        db.collection("Users")
                .document(firebaseUser.getUid())
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String name = documentSnapshot.getString("name");
                        String email = documentSnapshot.getString("email");
                        String city = documentSnapshot.getString("city");
                        String postNr = documentSnapshot.getString("postNr");
                        String street = documentSnapshot.getString("street");

                        profileName.setText(name != null ? name : "");
                        profileEmail.setText(email != null ? email : "");
                        profileCity.setText(city != null ? city : "");
                        profilePostNr.setText(postNr != null ? postNr : "");
                        profileStreet.setText(street != null ? street : "");
                    } else {
                        Log.d(LOG_TAG, "Nem található felhasználói dokumentum");
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Hiba a profil betöltésekor: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e(LOG_TAG, "Profil betöltési hiba", e);
                });
    }

    private void updateProfileData() {
        if (firebaseUser == null) {
            Toast.makeText(this, "Nem vagy bejelentkezve!", Toast.LENGTH_SHORT).show();
            return;
        }


        String name = profileName.getText().toString().trim();
        String city = profileCity.getText().toString().trim();
        String postNr = profilePostNr.getText().toString().trim();
        String street = profileStreet.getText().toString().trim();

        Map<String, Object> updatedData = new HashMap<>();
        updatedData.put("name", name);
        updatedData.put("city", city);
        updatedData.put("postNr", postNr);
        updatedData.put("street", street);


        db.collection("Users")
                .document(firebaseUser.getUid())
                .update(updatedData)
                .addOnSuccessListener(aVoid ->
                        Toast.makeText(this, "Profil frissítve!", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Hiba a mentéskor: " + e.getMessage(), Toast.LENGTH_LONG).show());
    }


    private void deleteProfile() {
        if (firebaseUser == null) {
            Toast.makeText(this, "Nem vagy bejelentkezve!", Toast.LENGTH_SHORT).show();
            return;
        }

        String uid = firebaseUser.getUid();

        db.collection("Users").document(uid)
                .delete()
                .addOnSuccessListener(unused -> {
                    Toast.makeText(this, "Adatok törölve a Firestore-ból", Toast.LENGTH_SHORT).show();

                    firebaseUser.delete()
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(this, "Felhasználó törölve", Toast.LENGTH_SHORT).show();
                                FirebaseAuth.getInstance().signOut();

                                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(this, "Nem sikerült a felhasználó törlése: " + e.getMessage(), Toast.LENGTH_LONG).show();
                            });

                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Hiba az adatok törlésekor: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
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
            finish();
            return true;
        } else if (id == R.id.navBasket) {
            Log.d(LOG_TAG, "Basket clicked!");
            Intent intent = new Intent(this, BasketActivity.class);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
            return true;
        } else if (id == R.id.navProfile) {
            Log.d(LOG_TAG, "Profile clicked!");
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