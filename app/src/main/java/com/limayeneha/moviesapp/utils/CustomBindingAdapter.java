package com.limayeneha.moviesapp.utils;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.limayeneha.moviesapp.R;
import com.squareup.picasso.Picasso;

public class CustomBindingAdapter {

    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {

        Picasso.get()
                .load(imageUrl)
                .error(R.drawable.ic_launcher_background)
                .into(view);
    }
}
