package com.example.furniturewebshop;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getName();
    private static final String PREF_KEY = String.valueOf(MainActivity.class.getPackage());
    private static final int SECRET_KEY = 99;
    private SharedPreferences preferences;
    private FirebaseAuth firebaseAuth;
    private RecyclerView recyclerView;
    private ArrayList<RoomItem> roomList;


    EditText emailET;
    EditText passwordET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        getWindow().setExitTransition(new Slide());
        getWindow().setExitTransition(new Slide());
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        emailET = findViewById(R.id.loginEmail);
        passwordET = findViewById(R.id.loginPassword);

        preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);

        firebaseAuth = FirebaseAuth.getInstance();

        Log.i(LOG_TAG, "onCreate");

    }


    public void login(View view) {
        String email = String.valueOf(emailET.getText());
        String password = String.valueOf(passwordET.getText());

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Add meg az email címet és a jelszót!", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.i(LOG_TAG, "Bejelentkezett: " + email + ", jelszó: " + password);

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(LOG_TAG, "Sikeres bejelentkezés");
                    goToRoomList();
                } else {
                    Log.d(LOG_TAG, "Sikertelen bejelentkezés");
                    Toast.makeText(MainActivity.this, "Sikertelen bejelentkezés: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void goToRegistration(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.putExtra("SECRET_KEY", SECRET_KEY);
        startActivity(intent,
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    private void goToRoomList() {
        Intent intent = new Intent(this, RoomListActivity.class);
        startActivity(intent,
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    public void loginGuest(View view) {
        firebaseAuth.signInAnonymously().addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(LOG_TAG, "Sikeres anonim bejelentkezés");
                    goToRoomList();
                } else {
                    Log.d(LOG_TAG, "Sikertelen anonim bejelentkezés");
                    Toast.makeText(MainActivity.this, "Sikertelen anonim bejelentkezés: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

}