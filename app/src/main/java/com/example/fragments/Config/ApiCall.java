package com.example.fragments.Config;

import com.example.fragments.Model.Film.AddFilmBodyRequest;
import com.example.fragments.Model.Film.AddFilmResponse;
import com.example.fragments.Model.Film.FavFilmRequest;
import com.example.fragments.Model.Film.FavFilmResponse;
import com.example.fragments.Model.Film.ListFilmRequest;
import com.example.fragments.Model.Film.searchFilmModel;
import com.example.fragments.Model.List.ListModel;
import com.example.fragments.Model.List.ListRequest;
import com.example.fragments.Model.List.ListResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiCall {

    @GET("search/movie?")
    Call<searchFilmModel> getData(@Query("api_key") String api_key, @Query("query") String query);

    @GET("account/get2_audio2/favorite/movies?")
    Call<FavFilmRequest> getFavMovies(@Query("api_key") String api_key, @Query("session_id") String session_id);

    @POST("account/get2_audio2/favorite?")
    Call<FavFilmResponse> setFavMovies(@Query("api_key") String api_key, @Query("session_id") String session_id, @Body FavFilmResponse favFilmResponse);

    @POST("list?")
    Call<ListResponse> postList(@Query("api_key") String api_key, @Query("session_id") String session_id, @Body ListModel listModel);

    @GET("account/get2_audio2/lists?")
    Call<ListRequest> getLists(@Query("api_key") String api_key, @Query("language") String language, @Query("session_id") String session_id, @Query("page") int page);

    @GET("list/{list_id}?")
    Call<ListFilmRequest> getListMovies(@Path("list_id") int list_id, @Query("api_key") String api_key, @Query("language") String language);

    @POST("list/{list_id}/add_item?")
    Call<AddFilmResponse> addFilmToList(@Path("list_id") int list_id, @Query("api_key") String api_key, @Query("session_id") String session_id, @Body AddFilmBodyRequest addFilmBodyRequest);
}
