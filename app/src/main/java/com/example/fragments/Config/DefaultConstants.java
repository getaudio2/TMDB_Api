package com.example.fragments.Config;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DefaultConstants {

    public static final String API_KEY = "4a11d5ccaf1ae38b7c09353c16ec3fe6";
    public static final String SESSION_ID = "b00c43e50e8a9ce8fb465c4969a5f33a95e8816e";
    public static final String ACCOUNT_ID = "get2_audio2";

    public static final String BASE_IMG_URL = "https://image.tmdb.org/t/p/w500/";

    public static final Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://api.themoviedb.org/3/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

}
