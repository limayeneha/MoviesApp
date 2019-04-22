package com.limayeneha.moviesapp.model;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

import static com.limayeneha.moviesapp.model.Status.DETAIL;
import static com.limayeneha.moviesapp.model.Status.ERROR;
import static com.limayeneha.moviesapp.model.Status.LOADING;
import static com.limayeneha.moviesapp.model.Status.SUCCESS;

public class Response {

    public Status status;

    @Nullable
    public List<MovieItem> data;

    @Nullable
    public Integer index;

    @Nullable
    public Throwable error;

    @Nullable
    public String message;

    private Response(Status status, @Nullable List<MovieItem> data, @Nullable Integer index, @Nullable Throwable error, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.index = index;
        this.error = error;
        this.message = message;
    }

    public static Response loading() {
        return new Response(LOADING, null, null, null, null);
    }

    public static Response success(@NonNull List<MovieItem> data) {
        return new Response(SUCCESS, data, null, null, null);
    }

    public static Response detail(@NonNull int index, @NonNull String message) {
        return new Response(DETAIL, null, index, null, message);
    }

    public static Response error(@NonNull Throwable error) {
        return new Response(ERROR, null, null, error, null);
    }
}
