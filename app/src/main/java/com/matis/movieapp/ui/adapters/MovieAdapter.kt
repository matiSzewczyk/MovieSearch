package com.matis.movieapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.matis.movieapp.data.models.Discover.Result
import com.matis.movieapp.databinding.MovieRvItemBinding
import com.matis.movieapp.utils.CustomClickInterface

class MovieAdapter(
    private val movies: List<Result>,
    private val customClickInterface: CustomClickInterface
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(
        val binding: MovieRvItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            apply {
                itemView.setOnClickListener {
                    customClickInterface.onClickListener(
                        movies[adapterPosition].id
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            MovieRvItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.binding.apply {
            Glide.with(moviePoster.context)
                .load("https://image.tmdb.org/t/p/w500" + movies[position].poster_path)
                .override(375, 375)
                .into(moviePoster)
        }
    }

    override fun getItemCount() = movies.size
}