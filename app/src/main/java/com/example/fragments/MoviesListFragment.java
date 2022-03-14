package com.example.fragments;

import static com.example.fragments.Config.DefaultConstants.API_KEY;
import static com.example.fragments.Config.DefaultConstants.SESSION_ID;
import static com.example.fragments.Config.DefaultConstants.retrofit;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fragments.Config.ApiCall;
import com.example.fragments.Model.Film.FavFilmRequest;
import com.example.fragments.Model.Film.Film;
import com.example.fragments.Model.Film.ListFilmRequest;
import com.example.fragments.Model.Film.searchFilmModel;
import com.example.fragments.Recyclers.SearchMovieRecyclerViewAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MoviesListFragment extends Fragment {

    public String sectionTitle;
    RecyclerView recyclerView;
    public int id_list;

    public MoviesListFragment() {
        // Required empty public constructor
    }

    public MoviesListFragment(String title) {
        this.sectionTitle = title;
    }

    public MoviesListFragment(String name, int id_list) {
        this.sectionTitle = name;
        this.id_list = id_list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movies_list, container, false);

        TextView txtSectionTitle = view.findViewById(R.id.sectionTitle);
        txtSectionTitle.setText(sectionTitle);
        recyclerView = view.findViewById(R.id.recyclerSearch);

        if (sectionTitle.equals("Favourite movies")) {
            ApiCall apiCall = retrofit.create(ApiCall.class);
            Call<FavFilmRequest> call = apiCall.getFavMovies(API_KEY, SESSION_ID);

            call.enqueue(new Callback<FavFilmRequest>(){
                @Override
                public void onResponse(Call<FavFilmRequest> call, Response<FavFilmRequest> response) {
                    if(response.code()!=200){
                        Log.i("testApi", "checkConnection");
                        return;
                    }else {
                        ArrayList<Film> arraySearch = new ArrayList<>();
                        arraySearch = response.body().getResults();
                        callRecycler(arraySearch);
                    }
                }

                @Override
                public void onFailure(Call<FavFilmRequest> call, Throwable t) {

                }
            });
        } else {
            ApiCall apiCall = retrofit.create(ApiCall.class);
            Call<ListFilmRequest> call = apiCall.getListMovies(id_list, API_KEY, "en-US");

            call.enqueue(new Callback<ListFilmRequest>(){
                @Override
                public void onResponse(Call<ListFilmRequest> call, Response<ListFilmRequest> response) {
                    if(response.code()!=200){
                        Log.i("testApi", "checkConnection");
                        return;
                    }else {
                        ArrayList<Film> arraySearch = new ArrayList<>();
                        arraySearch = response.body().getResults();
                        callRecycler(arraySearch);
                    }
                }

                @Override
                public void onFailure(Call<ListFilmRequest> call, Throwable t) {

                }
            });
        }

        return view;
    }

    public void callRecycler(ArrayList<Film> arraySearch){
        SearchMovieRecyclerViewAdapter adapter = new SearchMovieRecyclerViewAdapter(arraySearch, getContext());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        recyclerView.setAdapter(adapter);
    }
}