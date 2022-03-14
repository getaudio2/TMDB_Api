package com.example.fragments.Model.List;

import com.example.fragments.Model.List.ListModel;

import java.util.ArrayList;

public class ListRequest {
    public int page;
    public ArrayList<ListModel> results;

    public int getPage() {
        return page;
    }

    public ArrayList<ListModel> getResults() {
        return results;
    }
}
