package com.app.appmovie.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.appmovie.databinding.ViewMovieItemBinding
import com.app.appmovie.models.MoviesTop
import com.bumptech.glide.Glide

class MoviesAdapter(var movies:List<MoviesTop>, private val movieClickedListener: (MoviesTop)->Unit):
    RecyclerView.Adapter<MoviesAdapter.ViewHoler>(){
    inner class ViewHoler(private val binding: ViewMovieItemBinding):RecyclerView.ViewHolder(binding.root) {

        fun bind(movie:MoviesTop){
            binding.tvTitle.text=movie.title
            binding.tvCalificacion.text= movie.vote_average.toString()
            Glide.with(binding.root.context).
            load("https://image.tmdb.org/t/p/w185/${movie.poster_path}")
                .into(binding.ivImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHoler {
        val binding=ViewMovieItemBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false
        )


        return ViewHoler(binding)
    }

    override fun onBindViewHolder(holder: ViewHoler, position: Int) {
        val movie=movies[position]
        holder.bind(movie)
        holder.itemView.setOnClickListener { movieClickedListener(movie) }
    }

    override fun getItemCount(): Int {
        return movies.size
    }
}