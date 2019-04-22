package com.limayeneha.moviesapp.ui;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.limayeneha.moviesapp.R;
import com.limayeneha.moviesapp.databinding.ListMovieItemBinding;
import com.limayeneha.moviesapp.model.MovieItem;
import com.limayeneha.moviesapp.utils.OnItemClickListener;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.UserViewHolder> {


    private List<MovieItem> movieItems;
    private Context context;
    MovieItem movieItem;
    UserViewHolder holder;
    private final OnItemClickListener mListener;

    public MovieAdapter(Context context, List<MovieItem> movieItems, OnItemClickListener listener) {
        this.context = context;
        this.movieItems = movieItems;
        this.mListener = listener;
    }


    public class UserViewHolder extends RecyclerView.ViewHolder {

        ListMovieItemBinding binding;

        public UserViewHolder(ListMovieItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(final MovieItem movieItem) {
            binding.setMovie(movieItem);
            binding.itemView.setOnClickListener(v -> mListener.onItemClick(v, movieItem));
            binding.executePendingBindings();
        }
    }

    public void setMovieItems(List<MovieItem> movieItems) {
        this.movieItems = movieItems;
        notifyDataSetChanged();
    }

    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                             int viewType) {
        ListMovieItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(
                parent.getContext()), R.layout.list_movie_item, parent, false);
        final UserViewHolder mViewHolder = new UserViewHolder(binding);
        return mViewHolder;
    }


    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        movieItem = movieItems.get(position);
        holder.bind(movieItem);

    }

    @Override
    public int getItemCount() {
        return movieItems.size();
    }


}
