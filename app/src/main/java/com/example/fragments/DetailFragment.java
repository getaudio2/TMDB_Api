package com.example.fragments;

import static com.example.fragments.Config.DefaultConstants.API_KEY;
import static com.example.fragments.Config.DefaultConstants.BASE_IMG_URL;
import static com.example.fragments.Config.DefaultConstants.SESSION_ID;
import static com.example.fragments.Config.DefaultConstants.retrofit;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fragments.Config.ApiCall;
import com.example.fragments.Config.GlideApp;
import com.example.fragments.Model.Film.FavFilmResponse;
import com.example.fragments.Model.Film.Film;
import com.example.fragments.Model.Film.searchFilmModel;
import com.example.fragments.Model.List.List;
import com.example.fragments.Model.List.ListModel;
import com.example.fragments.Model.List.ListRequest;
import com.example.fragments.Recyclers.AddMovieListsRecyclerViewAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailFragment extends Fragment {


    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        Bundle bundle = getArguments();
        Film film = (Film) bundle.getSerializable("Film");

        TextView txtDetailTitle = view.findViewById(R.id.txtDetailTitle);
        TextView txtDetailDesc = view.findViewById(R.id.txtDetailDesc);
        ImageView imgDetail = view.findViewById(R.id.imgDetail);
        ImageButton btnFav = view.findViewById(R.id.btnFav);
        ImageButton btnAddtoList = view.findViewById(R.id.btnAddtoList);

        if(bundle.getBoolean("fav") == true){
            btnFav.setImageResource(R.drawable.ic_fav_on);
            Log.i("test", "boolean true aaaaa");
        }


        txtDetailTitle.setText(film.getOriginal_title());
        txtDetailDesc.setText(film.getOverview());

        GlideApp.with(getContext())
                .load(BASE_IMG_URL + film.getPoster_path())
                .centerCrop()
                .into(imgDetail);

        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnFav.setImageResource(R.drawable.ic_fav_on);

                bundle.putBoolean("fav", true);

                FavFilmResponse favFilmResponse = new FavFilmResponse(film.getId());
                favFilmResponse.setFavorite(true);
                favFilmResponse.setMedia_type("movie");

                ApiCall apiCall = retrofit.create(ApiCall.class);
                Call<FavFilmResponse> call = apiCall.setFavMovies(API_KEY, SESSION_ID, favFilmResponse);

                call.enqueue(new Callback<FavFilmResponse>() {
                    @Override
                    public void onResponse(Call<FavFilmResponse> call, Response<FavFilmResponse> response) {
                        if (response.code() != 201) {
                            Log.i("testApi", "checkConnection");
                            return;
                        } else {
                            Log.i("SET AS FAVOURITE ", "DONE");
                            Log.i("response message", "e" + response.raw());
                        }
                    }

                    @Override
                    public void onFailure(Call<FavFilmResponse> call, Throwable t) {
                        t.getCause();
                    }
                });
            }
        });

        btnAddtoList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });


        return view;

    }

    public void showDialog(){
        View alertCustomdialog = getLayoutInflater().inflate( R.layout.form_movie_to_list, null);

        //initialize alert builder.
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

        //set our custom alert dialog to tha alertdialog builder
        alert.setView(alertCustomdialog);

        final AlertDialog dialog = alert.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();

        ApiCall apiCall = retrofit.create(ApiCall.class);
        Call<ListRequest> call = apiCall.getLists(API_KEY, "en-US", SESSION_ID, 1);

        call.enqueue(new Callback<ListRequest>(){
            @Override
            public void onResponse(Call<ListRequest> call, Response<ListRequest> response) {
                if(response.code()!=200){
                    Log.i("testApi", "checkConnection");
                    return;
                }else {
                    ArrayList<ListModel> arrayLists = new ArrayList<>();
                    arrayLists = response.body().getResults();
                    arrayLists.add(new ListModel("Comedia", 8));
                    arrayLists.add(new ListModel("Ciència", 8));
                    arrayLists.add(new ListModel("Terror", 8));
                    arrayLists.add(new ListModel("Comedia", 8));
                    arrayLists.add(new ListModel("Ciència", 8));
                    arrayLists.add(new ListModel("Terror", 8));
                    arrayLists.add(new ListModel("Comedia", 8));
                    arrayLists.add(new ListModel("Ciència", 8));
                    arrayLists.add(new ListModel("Terror", 8));
                    callRecycler(arrayLists, alertCustomdialog, dialog);
                }
            }

            @Override
            public void onFailure(Call<ListRequest> call, Throwable t) {

            }
        });

    }

    public void callRecycler(ArrayList<ListModel> arrayLists, View alertCustomdialog, AlertDialog dialog) {
        RecyclerView recyclerView = alertCustomdialog.findViewById(R.id.recyclerList);
        AddMovieListsRecyclerViewAdapter adapter = new AddMovieListsRecyclerViewAdapter(arrayLists, getContext(), dialog);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

}