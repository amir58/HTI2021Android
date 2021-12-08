package com.amirmohammed.hti2021androidone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.amirmohammed.hti2021androidone.login.LoginActivity;
import com.amirmohammed.hti2021androidone.maps.MapHomeActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "Splash";
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        firestore.setFirestoreSettings(settings);

        Log.i(TAG, "onCreate: " + Thread.currentThread().getName());

//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Log.i(TAG, "run: " + Thread.currentThread().getName());
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                Log.i(TAG, "onCreate: end sleep");
//
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(SplashActivity.this, "Hello from worker thread", Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//            }
//        });
//        thread.start();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1500);
                    if (firebaseAuth.getCurrentUser() == null){
                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Intent intent = new Intent(SplashActivity.this, MapHomeActivity.class);
                        startActivity(intent);
                    }
                    finish();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}