package com.app.appmovie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.app.appmovie.databinding.ViewMovieNowItemBinding
import com.app.appmovie.models.MoviesNow
import com.bumptech.glide.Glide
import java.util.*

class MovieAdapterNow(var moviesNow:List<MoviesNow>,
      private val clickOn:(MoviesNow)->Unit):RecyclerView.Adapter<MovieAdapterNow.ViewHolder>(),Filterable{

    var filerterMoviesNow:List<MoviesNow> = moviesNow
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
        val movie=filerterMoviesNow[position]
        holder.bind(movie)
        holder.itemView.setOnClickListener { clickOn(movie) }
    }

    override fun getItemCount(): Int {
        return filerterMoviesNow.size
    }

    override fun getFilter(): Filter {
        return object :Filter(){
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val query=p0.toString().lowercase(Locale.getDefault())

                val filter=if(query.isEmpty()){
                        moviesNow
                    }else{
                        moviesNow.filter {
                            it.title.lowercase(Locale.getDefault()).contains(query)
                        }
                    }
                val filterResult=FilterResults()
                filterResult.values=filter
                return filterResult
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                filerterMoviesNow=p1?.values as List<MoviesNow>
                notifyDataSetChanged()
            }

        }
    }
}