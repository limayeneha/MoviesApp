package com.limayeneha.moviesapp.model;

import com.google.gson.annotations.SerializedName;

public class MovieItem {
    @SerializedName("id")
    public int id;

    @SerializedName("title")
    public String title;

    @SerializedName("image")
    public String image;
}
