package com.amirmohammed.hti2021androidone;

import androidx.room.TypeConverter;

import com.google.gson.Gson;

public class RoomConverters {

    @TypeConverter
    public static String userToJson(User user){
        return new Gson().toJson(user);
    }

    @TypeConverter
    public static User userFromJson(String json){
        return new Gson().fromJson(json, User.class);
    }


}
