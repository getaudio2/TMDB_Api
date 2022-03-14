package com.example.fragments.Model.Film;

import java.util.ArrayList;

public class ListFilmRequest {
    public int listId;
    public ArrayList<Film> results;

    public int getListId() {
        return listId;
    }

    public ArrayList<Film> getResults() {
        return results;
    }
}
