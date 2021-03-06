package com.example.fragments;

import static com.example.fragments.Config.DefaultConstants.API_KEY;
import static com.example.fragments.Config.DefaultConstants.SESSION_ID;
import static com.example.fragments.Config.DefaultConstants.retrofit;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fragments.Config.ApiCall;
import com.example.fragments.Model.Film.FavFilmRequest;
import com.example.fragments.Model.Film.Film;
import com.example.fragments.Model.Film.searchFilmModel;
import com.example.fragments.Model.List.ListModel;
import com.example.fragments.Model.List.ListRequest;
import com.example.fragments.Model.List.ListResponse;
import com.example.fragments.Recyclers.AddMovieListsRecyclerViewAdapter;
import com.example.fragments.Recyclers.SearchMovieRecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListFragment extends Fragment {

    RecyclerView recyclerView;

    public ListFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, container, false);

        FloatingActionButton btnAdd = view.findViewById(R.id.btnAddList);

        recyclerView = view.findViewById(R.id.recyclerLists);

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
                    callRecycler(arrayLists);
                }
            }

            @Override
            public void onFailure(Call<ListRequest> call, Throwable t) {

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        return view;
    }

    public void callRecycler(ArrayList<ListModel> arrayLists){
        AddMovieListsRecyclerViewAdapter adapter = new AddMovieListsRecyclerViewAdapter(arrayLists, getContext());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        recyclerView.setAdapter(adapter);
    }

    public void showDialog(){
        View alertCustomdialog = getLayoutInflater().inflate( R.layout.form_add_list, null);

        //initialize alert builder.
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

        //set our custom alert dialog to tha alertdialog builder
        alert.setView(alertCustomdialog);

        final AlertDialog dialog = alert.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();

        Button btnSaveList = alertCustomdialog.findViewById(R.id.btnSaveList);
        EditText txtListName = alertCustomdialog.findViewById(R.id.txtList);
        EditText txtListDesc = alertCustomdialog.findViewById(R.id.txtDescription);

        btnSaveList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListModel listModel = new ListModel(txtListName.getText().toString(), txtListDesc.getText().toString());

                ApiCall apiCall = retrofit.create(ApiCall.class);
                Call<ListResponse> call = apiCall.postList(API_KEY, SESSION_ID, listModel);

                call.enqueue(new Callback<ListResponse>(){
                    @Override
                    public void onResponse(Call<ListResponse> call, Response<ListResponse> response) {
                        if(response.code()!=201){
                            Log.i("testApi", "checkConnection");
                            return;
                        }else {
                            Log.i("CREATE LIST ", "DONE");
                            Toast.makeText(getContext(),"List created",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ListResponse> call, Throwable t) {

                    }
                });

                dialog.dismiss();
            }
        });
    }
}