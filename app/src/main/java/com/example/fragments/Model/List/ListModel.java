package com.example.fragments.Model.List;

public class ListModel {
    public String name;
    public String description;
    public String language;
    public int favorite_count;
    public int id;
    public int item_count;
    public String list_type;
    public String poster_path;

    public ListModel(String name, int item_count) {
        this.name = name;
        this.description = "Default desc";
        this.language = "en-US";
        this.item_count = item_count;
    }

    public ListModel(String name, String description) {
        this.name = name;
        this.description = description;
        this.language = "en-US";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getFavorite_count() {
        return favorite_count;
    }

    public void setFavorite_count(int favorite_count) {
        this.favorite_count = favorite_count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getList_type() {
        return list_type;
    }

    public void setList_type(String list_type) {
        this.list_type = list_type;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public int getItem_count() {
        return item_count;
    }

    public void setItem_count(int item_count) {
        this.item_count = item_count;
    }
}
