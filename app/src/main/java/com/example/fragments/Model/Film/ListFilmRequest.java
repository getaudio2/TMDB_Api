package com.example.fragments.Model.Film;

import java.util.ArrayList;

public class ListFilmRequest {
    public int id;
    public ArrayList<Film> items;

    public int getListId() {
        return id;
    }

    public ArrayList<Film> getItems() {
        return items;
    }
}
