package com.example.fragments.Model.List;

public class List {
    public String title;
    public int count;

    public List(String title, int count) {
        this.title = title;
        this.count = count;
    }

    public String getTitle() {
        return title;
    }

    public int getCount() {
        return count;
    }
}
