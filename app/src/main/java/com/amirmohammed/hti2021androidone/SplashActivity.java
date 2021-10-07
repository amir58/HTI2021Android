package com.amirmohammed.hti2021androidone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.amirmohammed.hti2021androidone.database.Keys;
import com.amirmohammed.hti2021androidone.database.MyShared;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        MyShared.init(getApplicationContext());

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1500);

                    Intent intent;
                    if (MyShared.read(Keys.TOKEN, "").isEmpty()) {
                        intent = new Intent(SplashActivity.this, LoginActivity.class);
                    } else {
                        intent = new Intent(SplashActivity.this, MainActivity.class);
                    }

                    startActivity(intent);
                    finish();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println(e.getLocalizedMessage());
                }

            }
        });
        thread.start();
    }
}