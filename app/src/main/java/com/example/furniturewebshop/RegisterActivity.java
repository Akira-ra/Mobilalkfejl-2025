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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private static final String LOG_TAG = RegisterActivity.class.getName();
    private static final String PREF_KEY = String.valueOf(MainActivity.class.getPackage());
    private static final int SECRET_KEY = 99;
    private SharedPreferences preferences;
    private FirebaseAuth firebaseAuth;

    EditText nameET;
    EditText emailET;
    EditText passwordET;
    EditText passwordAgainET;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        getWindow().setExitTransition(new Slide());
        getWindow().setExitTransition(new Slide());
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        int secret_key = getIntent().getIntExtra("SECRET_KEY", 0);

        if (secret_key != 99) {
            finish();
        }

        nameET = findViewById(R.id.registerName);
        emailET = findViewById(R.id.registerEmail);
        passwordET = findViewById(R.id.registerPassword);
        passwordAgainET = findViewById(R.id.registerPasswordAgain);

        preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);
        String name = preferences.getString("name", "");
        String password = preferences.getString("password", "");

        nameET.setText(name);
        passwordET.setText(password);
        passwordAgainET.setText(password);

        firebaseAuth = FirebaseAuth.getInstance();

        Log.i(LOG_TAG, "onCreate");
    }

    public void register(View view) {
        String name = nameET.getText().toString();
        String email = emailET.getText().toString();
        String password = passwordET.getText().toString();
        String passwordConfirm = passwordAgainET.getText().toString();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty()) {
            Toast.makeText(this, "Adj meg minden adatot!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(passwordConfirm)) {
            Log.e(LOG_TAG, "Nem egyenlő a jelszó és a megerősítése.");
            return;
        }

        Log.i(LOG_TAG, "Regisztrált: " + name + ", e-mail: " + email);

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(LOG_TAG, "Új felhasználó létrehozása sikeres");
                    goToRoomListActivity();
                } else {
                    Log.d(LOG_TAG, "Új felhasználó létrehozása sikertelen");
                    Toast.makeText(RegisterActivity.this, "Új felhasználó létrehozása sikertelen: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void goToRoomListActivity(/* registered used class */) {
        Intent intent = new Intent(this, RoomListActivity.class);
       // intent.putExtra("SECRET_KEY", SECRET_KEY);
        startActivity(intent,
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    public void cancel(View view) {
        finish();
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