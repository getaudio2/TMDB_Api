package com.example.fragments.Recyclers;


import static com.example.fragments.Config.DefaultConstants.API_KEY;
import static com.example.fragments.Config.DefaultConstants.BASE_IMG_URL;
import static com.example.fragments.Config.DefaultConstants.SESSION_ID;
import static com.example.fragments.Config.DefaultConstants.retrofit;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fragments.Config.ApiCall;
import com.example.fragments.Config.GlideApp;
import com.example.fragments.DetailFragment;
import com.example.fragments.Model.Film.AddFilmBodyRequest;
import com.example.fragments.Model.Film.AddFilmResponse;
import com.example.fragments.Model.Film.Film;
import com.example.fragments.Model.Film.ListFilmRequest;
import com.example.fragments.Model.List.List;
import com.example.fragments.Model.List.ListModel;
import com.example.fragments.MoviesListFragment;
import com.example.fragments.R;

import java.io.Serializable;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMovieListsRecyclerViewAdapter extends RecyclerView.Adapter<AddMovieListsRecyclerViewAdapter.ViewHolder> {
    private ArrayList<ListModel> arrayList;
    private Context context;
    private AlertDialog dialog;
    private Film film;

    public AddMovieListsRecyclerViewAdapter(ArrayList<ListModel> arrN, Context c){
        this.arrayList = arrN;
        this.context = c;
    }

    public AddMovieListsRecyclerViewAdapter(ArrayList<ListModel> arrN, Context c, AlertDialog dialog){
        this.arrayList = arrN;
        this.context = c;
        this.dialog = dialog;
    }

    public AddMovieListsRecyclerViewAdapter(ArrayList<ListModel> arrN, Context c, AlertDialog dialog, Film film){
        this.arrayList = arrN;
        this.context = c;
        this.dialog = dialog;
        this.film = film;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {

        holder.listTitle.setText(arrayList.get(i).getName());
        holder.itemCount.setText(String.valueOf(arrayList.get(i).getItem_count()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("select", arrayList.get(i).getName());

                ListModel listModel = arrayList.get(i);

                AppCompatActivity app = (AppCompatActivity) view.getContext();

                if (dialog != null) {
                    dialog.dismiss();
                }

                if (film != null) {
                    AddFilmBodyRequest addFilmBodyRequest = new AddFilmBodyRequest();
                    addFilmBodyRequest.setMedia_id(film.getId());

                    ApiCall apiCall = retrofit.create(ApiCall.class);
                    Call<AddFilmResponse> call = apiCall.addFilmToList(listModel.getId(), API_KEY, SESSION_ID, addFilmBodyRequest);

                    call.enqueue(new Callback<AddFilmResponse>(){
                        @Override
                        public void onResponse(Call<AddFilmResponse> call, Response<AddFilmResponse> response) {
                            if(response.code()!=201){
                                Log.i("testApi", "checkConnection");
                                return;
                            }else {
                                Log.i("ADDED FILM TO LIST ", "DONE");
                                Toast.makeText(context,"Movie added correctly",Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<AddFilmResponse> call, Throwable t) {

                        }
                    });
                }

                MoviesListFragment moviesListFragment = new MoviesListFragment(listModel.getName(), listModel.getId());

                app.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, moviesListFragment).commit();
            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView listTitle;
        TextView itemCount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            listTitle = itemView.findViewById(R.id.listTitle);
            itemCount= itemView.findViewById(R.id.itemCount);
        }
    }
}

