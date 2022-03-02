package com.example.fragments;

import static com.example.fragments.Config.DefaultConstants.API_KEY;
import static com.example.fragments.Config.DefaultConstants.SESSION_ID;
import static com.example.fragments.Config.DefaultConstants.retrofit;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fragments.Config.ApiCall;
import com.example.fragments.Model.Film.FavFilmRequest;
import com.example.fragments.Model.Film.Film;
import com.example.fragments.Model.Film.searchFilmModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MoviesListFragment extends Fragment {

    public String sectionTitle;

    public MoviesListFragment() {
        // Required empty public constructor
    }

    public MoviesListFragment(String title) {
        this.sectionTitle = title;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movies_list, container, false);

        TextView txtSectionTitle = view.findViewById(R.id.sectionTitle);
        txtSectionTitle.setText(sectionTitle);

        ApiCall apiCall = retrofit.create(ApiCall.class);
        Call<FavFilmRequest> call = apiCall.getFavMovies(API_KEY, SESSION_ID);

        call.enqueue(new Callback<searchFilmModel>(){
            @Override
            public void onResponse(Call<searchFilmModel> call, Response<searchFilmModel> response) {
                if(response.code()!=200){
                    Log.i("testApi", "checkConnection");
                    return;
                }else {

                }
            }

            @Override
            public void onFailure(Call<searchFilmModel> call, Throwable t) {

            }
        });

        return view;
    }
}