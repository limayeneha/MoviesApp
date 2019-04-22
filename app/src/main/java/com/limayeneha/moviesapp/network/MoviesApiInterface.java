package com.limayeneha.moviesapp.network;

import com.limayeneha.moviesapp.model.MovieDetail;
import com.limayeneha.moviesapp.model.MovieItem;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MoviesApiInterface {

    @GET("api/movies")
    Observable<List<MovieItem>> getMovieList();

    @GET("api/movies/{movie_id}")
    Single<MovieDetail> getMovieDetail(@Path("movie_id") int movieId);
}
