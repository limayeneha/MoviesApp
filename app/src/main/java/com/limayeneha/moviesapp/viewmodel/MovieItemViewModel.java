package com.limayeneha.moviesapp.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.limayeneha.moviesapp.SingleLiveEvent;
import com.limayeneha.moviesapp.model.Response;
import com.limayeneha.moviesapp.network.LRUCache;
import com.limayeneha.moviesapp.network.MoviesApiInterface;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MovieItemViewModel extends ViewModel {

    private MoviesApiInterface moviesApiInterface;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<Response> response = new MutableLiveData<>();
    private final SingleLiveEvent<Response> responseDetail = new SingleLiveEvent<>();
    LRUCache lruCache = new LRUCache(5);

    public MovieItemViewModel(MoviesApiInterface moviesApiInterface) {
        this.moviesApiInterface = moviesApiInterface;
    }

    public void getMovieList() {
        disposables.add(moviesApiInterface.getMovieList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(__ -> response.setValue(Response.loading()))
                .subscribe(
                        movieItems -> response.setValue(Response.success(movieItems)),
                        throwable -> response.setValue(Response.error(throwable))
                )
        );
    }

    public void getMovieDetail(int movieId) {
        boolean isValid = lruCache.isValid(movieId);
        if( isValid ) {
            responseDetail.setValue(Response.detail(lruCache.get(movieId), "From Cache"));
        } else {
            disposables.add(moviesApiInterface.getMovieDetail(movieId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            movieDetail -> {
                                responseDetail.setValue(Response.detail(movieDetail.index, "From api"));
                                lruCache.put(movieId, movieDetail.index);
                            },
                            throwable -> responseDetail.setValue(Response.error(throwable))
                    )
            );
        }
    }

    public MutableLiveData<Response> response() {
        return response;
    }

    public SingleLiveEvent<Response> responseDetail() {
        return responseDetail;
    }

    @Override
    protected void onCleared() {
        disposables.clear();
    }
}
