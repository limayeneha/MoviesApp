package com.limayeneha.moviesapp.ui;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.limayeneha.moviesapp.MovieAppApplication;
import com.limayeneha.moviesapp.R;
import com.limayeneha.moviesapp.databinding.ActivityMoviesListBinding;
import com.limayeneha.moviesapp.model.MovieItem;
import com.limayeneha.moviesapp.model.Response;
import com.limayeneha.moviesapp.viewmodel.MovieItemViewModel;
import com.limayeneha.moviesapp.viewmodel.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class MoviesListActivity extends AppCompatActivity {

    ProgressBar loadingProgress;
    RecyclerView movieListRecyclerView;
    MovieAdapter movieAdapter;
    MovieItemViewModel movieItemViewModel;
    List<MovieItem> movies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMoviesListBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_movies_list);

        loadingProgress = binding.loadingProgress;

        ViewModelFactory mViewModelFactory = MovieAppApplication.provideViewModelFactory(this);

        movieItemViewModel = ViewModelProviders.of(this, mViewModelFactory).get(MovieItemViewModel.class);

        movieItemViewModel.getMovieList();
        movieItemViewModel.response().observe(this, response -> processResponse(response));

        movieAdapter = new MovieAdapter(this, new ArrayList<>(), (view, movieItem) -> {
            movieItemViewModel.getMovieDetail(movieItem.id);
            movieItemViewModel.responseDetail().observe(MoviesListActivity.this, response -> processResponseDetail(response));

        });

        movieListRecyclerView = binding.movielistRecyclerView;

        movieListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        movieListRecyclerView.setAdapter(movieAdapter);
    }

    private void processResponse(Response response) {
        switch (response.status) {
            case LOADING:
                renderLoadingState();
                break;

            case SUCCESS:
                renderDataState(response.data);
                break;

            case ERROR:
                renderErrorState(response.error);
                break;
        }
    }

    private void processResponseDetail(Response response) {
        switch (response.status) {

            case DETAIL:
                renderDetailState(response.index, response.message);
                break;
        }
    }

    private void renderLoadingState() {
        loadingProgress.setVisibility(ProgressBar.VISIBLE);
        movieListRecyclerView.setVisibility(View.GONE);
    }

    private void renderDataState(List<MovieItem> movieItems) {
        loadingProgress.setVisibility(ProgressBar.GONE);
        movieListRecyclerView.setVisibility(View.VISIBLE);
        movies = movieItems;
        movieAdapter.setMovieItems(movies);
    }

    private void renderDetailState(int index, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(MoviesListActivity.this).create(); //Read Update
        alertDialog.setTitle(getString(R.string.dialog_title));
        alertDialog.setMessage(getString(R.string.dialog_message, String.valueOf(index)));
        Toast.makeText(MoviesListActivity.this, message, Toast.LENGTH_LONG).show();
        alertDialog.setCanceledOnTouchOutside(true);

        alertDialog.show();
    }

    private void renderErrorState(Throwable throwable) {
        loadingProgress.setVisibility(View.GONE);
        movieListRecyclerView.setVisibility(View.GONE);
        Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show();
    }
}
