package com.amirmohammed.hti2021androidone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.databinding.DataBindingUtil;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.amirmohammed.hti2021androidone.database.Keys;
import com.amirmohammed.hti2021androidone.database.MyShared;
import com.amirmohammed.hti2021androidone.databinding.ActivityLoginBinding;
import com.amirmohammed.hti2021androidone.models.Source;
import com.amirmohammed.hti2021androidone.models.login.LoginErrorResponse;
import com.amirmohammed.hti2021androidone.models.login.LoginResponse;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

// https://ecommerce-api.senior-consultingco.com/api/login
// baseUrl : https://ecommerce-api.senior-consultingco.com/api/`
// endPoint : login
public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

    }

    public void login(View view) {
//        EditText editTextEmail, editTextPassword;
//        editTextEmail = findViewById(R.id.login_et_email);
//        editTextPassword = findViewById(R.id.login_et_password);

        String email = binding.loginEtEmail.getText().toString();

        if (email.isEmpty()) {
            Toast.makeText(this, "email required", Toast.LENGTH_SHORT).show();
            return;
        }
        String password = binding.loginEtPassword.getText().toString();

        if (password.isEmpty()) {
            Toast.makeText(this, "password required", Toast.LENGTH_SHORT).show();
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ecommerce-api.senior-consultingco.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoginApis loginApis = retrofit.create(LoginApis.class);

        loginApis.login(email, password).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    System.out.println("TOKEN => " + response.body().getData().getAccessToken());
                } else {
                    System.out.println(response.errorBody());

                    Gson gson = new Gson();
                    LoginErrorResponse loginErrorResponse =
                            gson.fromJson(response.errorBody().charStream(), LoginErrorResponse.class);

                    Toast.makeText(LoginActivity.this, loginErrorResponse.getMessage()
                            , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                System.out.println(t.getLocalizedMessage());
            }
        });

    }


    void sharedWithGSON() {
        User user = new User(1, "Amir", "20");

        Gson gson = new Gson();
        String userString = gson.toJson(user);

//        SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
//        preferences.edit().putString("user", userString).apply();
        MyShared.write(Keys.USER, userString);


//        String userJson = preferences.getString("user", "");
        String userJson = MyShared.read(Keys.USER, "");
        User user2 = gson.fromJson(userJson, User.class);

    }

    public void notify(View view) {
        final String CHANNEL_ID = "CHANNEL_ID";
        NotificationManager notificationManager = getSystemService(NotificationManager.class);

        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .setContentTitle("First notification")
                .setContentText("HelloWorld!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "name";
            String description = "description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0, builder.build());

    }
}

interface LoginApis {

    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> login(
            @Field("email") String email,
            @Field("password") String password
    );

}

class User {
    int id;
    String name;
    String age;

    public User(int id, String name, String age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
}
