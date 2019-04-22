package com.limayeneha.moviesapp;

import android.app.Application;
import android.content.Context;
import com.limayeneha.moviesapp.network.MoviesApi;
import com.limayeneha.moviesapp.network.MoviesApiInterface;
import com.limayeneha.moviesapp.viewmodel.ViewModelFactory;

public class MovieAppApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static MoviesApiInterface provideUserDataSource(Context context) {
        return MoviesApi.getClient(context).create(MoviesApiInterface.class);
    }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        MoviesApiInterface apiInterface = provideUserDataSource(context);
        return new ViewModelFactory(apiInterface);
    }

}
