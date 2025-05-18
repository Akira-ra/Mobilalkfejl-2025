package com.example.furniturewebshop;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class PurchaseHistoryActivity extends AppCompatActivity {

    private ListView purchaseListView;
    private PurchaseAdapter adapter;
    private List<Purchase> purchaseList;
    private FirebaseFirestore db;
    private FirebaseUser currentUser;
    private TextView noOrdersText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_purchase_history);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        noOrdersText = findViewById(R.id.noOrdersText);

        purchaseListView = findViewById(R.id.purchaseListView);
        purchaseList = new ArrayList<>();
        adapter = new PurchaseAdapter(this, purchaseList);
        purchaseListView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            loadPurchases();
        }
    }

    private void loadPurchases() {
        db.collection("Purchases")
                .whereEqualTo("userid", currentUser.getUid())
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .limit(20)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots.isEmpty()) {
                        noOrdersText.setVisibility(View.VISIBLE);
                        purchaseListView.setVisibility(View.GONE);
                    } else {
                        noOrdersText.setVisibility(View.GONE);
                        purchaseListView.setVisibility(View.VISIBLE);

                        purchaseList.clear();
                        for (DocumentSnapshot doc : queryDocumentSnapshots) {
                            Purchase purchase = doc.toObject(Purchase.class);
                            purchaseList.add(purchase);
                        }
                        adapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(e -> {
                    noOrdersText.setText("Hiba történt a rendelések lekérdezésekor.");
                    noOrdersText.setVisibility(View.VISIBLE);
                    purchaseListView.setVisibility(View.GONE);
                });
    }

}
