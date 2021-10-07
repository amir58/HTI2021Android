package com.amirmohammed.hti2021androidone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;

import com.amirmohammed.hti2021androidone.database.Keys;
import com.amirmohammed.hti2021androidone.database.MyShared;
import com.amirmohammed.hti2021androidone.models.NewsResponse;
import com.amirmohammed.hti2021androidone.network.RetrofitClient;
import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
// https://newsapi.org/v2/top-headlines?country=eg&category=business&apiKey=fa72aea7f1af46a6a45be8aa23e21b64

// baseUrl : https://newsapi.org/
// endPoint : v2/top-headlines?country=eg&category=business&apiKey=fa72aea7f1af46a6a45be8aa23e21b64
public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerViewNews;
    NewsAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewNews = findViewById(R.id.rv_news);

//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://newsapi.org/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();

//        Apis apis = RetrofitClient.getInstance().create(Apis.class);

        RetrofitClient.getInstance().getNews("eg", "science")
                .enqueue(new Callback<NewsResponse>() {
                    @Override
                    public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                        if (response.isSuccessful()) {
                            System.out.println(response.body().getArticles().size());
                            newsAdapter = new NewsAdapter(response.body().getArticles());
                            recyclerViewNews.setAdapter(newsAdapter);
                        }
                    }

                    @Override
                    public void onFailure(Call<NewsResponse> call, Throwable t) {
                        System.out.println(t.getLocalizedMessage());
                    }
                });


    }

    void save() {
        SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
        preferences.edit().putString("token", "2kjlnd872t3xbludhsa8l73l8").apply();

        MyShared.write(Keys.TOKEN, "asjsknda3389=f23nf3n9san9as");
        MyShared.read(Keys.TOKEN, "");
    }


    void getToken() {
        SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
        preferences.getString("token", "");
    }


    @BindingAdapter("glide")
    public static void glide(ImageView imageView, String imageUrl){
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .placeholder(R.mipmap.ic_launcher)
                .into(imageView);
    }

}