package com.app.appmovie.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.app.appmovie.databinding.ViewMovieItemBinding
import com.app.appmovie.models.MoviesTop
import com.bumptech.glide.Glide
import java.util.*

class MoviesAdapter(var movies:List<MoviesTop>, private val movieClickedListener: (MoviesTop)->Unit):
    RecyclerView.Adapter<MoviesAdapter.ViewHoler>(),Filterable{
    var filteredList: List<MoviesTop> = movies

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
        val movie=filteredList[position]
        holder.bind(movie)
        holder.itemView.setOnClickListener { movieClickedListener(movie) }
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    override fun getFilter(): Filter {
        return object :Filter(){
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val query=p0.toString().lowercase(Locale.getDefault())

                val filter=
                    if (query.isEmpty()){
                        movies
                    }else{
                        movies.filter { it.title.lowercase(Locale.getDefault()).contains(query) }
                    }
                val resultFilter=FilterResults()
                resultFilter.values=filter
                return resultFilter
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                filteredList=p1?.values as List<MoviesTop>
                notifyDataSetChanged()
            }

        }
    }
}