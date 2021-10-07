package com.amirmohammed.hti2021androidone.network;

import com.amirmohammed.hti2021androidone.models.NewsResponse;
import com.amirmohammed.hti2021androidone.models.requests.NoteRequest;
import com.amirmohammed.hti2021androidone.models.requests.RegisterRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Apis {

    @GET("v2/top-headlines?country=eg&category=business&apiKey=fa72aea7f1af46a6a45be8aa23e21b64")
    Call<NewsResponse> getNews();


    @GET("v2/top-headlines?apiKey=fa72aea7f1af46a6a45be8aa23e21b64")
    Call<NewsResponse> getNews(@Query("country") String country,
                               @Query("category") String category);

    // Notes

    @FormUrlEncoded
    @POST("login")
    Call<String> login(
            @Field("email") String email,
            @Field("password") String password
    );

    @POST("register")
    Call<String> register(@Body RegisterRequest registerRequest);


    @POST("notes/add")
    Call<String> addNote(@Body NoteRequest noteRequest,
                         @Header("Authorization") String token);


    @POST("notes/edit/{noteId}")
    Call<String> editNote(
            @Path("noteId") String noteId,
            @Body NoteRequest noteRequest,
            @Header("Authorization") String token);


    @GET("notes")
    Call<List<String>> getNotes(@Header("Authorization") String token);



}
