package com.app.appmovie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.appmovie.databinding.ViewMovieNowItemBinding
import com.app.appmovie.models.MoviesNow
import com.bumptech.glide.Glide

class MovieAdapterNow(var moviesNow:List<MoviesNow>,
      private val clickOn:(MoviesNow)->Unit):RecyclerView.Adapter<MovieAdapterNow.ViewHolder>() {
    inner class ViewHolder(private val binding: ViewMovieNowItemBinding):RecyclerView.ViewHolder(binding.root) {

        fun bind(movie:MoviesNow){
            binding.tvTitle.text=movie.title
            binding.tvCalificacion.text= movie.vote_average.toString()
            Glide.with(binding.root.context).
            load("https://image.tmdb.org/t/p/w185/${movie.poster_path}")
                .into(binding.ivImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=ViewMovieNowItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie=moviesNow[position]
        holder.bind(movie)
        holder.itemView.setOnClickListener { clickOn(movie) }
    }

    override fun getItemCount(): Int {
        return moviesNow.size
    }
}