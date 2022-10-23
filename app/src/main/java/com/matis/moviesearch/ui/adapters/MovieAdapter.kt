package com.matis.moviesearch.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.matis.moviesearch.data.models.discover.Result
import com.matis.moviesearch.databinding.MovieRvItemBinding
import com.matis.moviesearch.utils.Constants
import com.matis.moviesearch.utils.CustomClickInterface

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
                        movies[adapterPosition].id,
                        movies[adapterPosition].name
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
                .load(Constants.IMAGE_URL + movies[position].poster_path)
                .override(450, 450)
                .into(moviePoster)
        }
    }

    override fun getItemCount() = movies.size
}