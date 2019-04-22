package com.limayeneha.moviesapp.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import com.limayeneha.moviesapp.network.MoviesApiInterface;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final MoviesApiInterface moviesApiInterface;

    public ViewModelFactory(MoviesApiInterface nytApiInterface) {
        this.moviesApiInterface = nytApiInterface;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MovieItemViewModel.class)) {
            return (T) new MovieItemViewModel(moviesApiInterface);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
