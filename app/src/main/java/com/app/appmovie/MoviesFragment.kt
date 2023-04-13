package com.app.appmovie

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.appmovie.adapter.MovieAdapterNow
import com.app.appmovie.adapter.MoviesAdapter
import com.app.appmovie.databinding.FragmentMoviesBinding
import com.app.appmovie.models.MoviesTop
import com.app.appmovie.models.MoviesNow
import com.app.appmovie.utils.ApiClient
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MoviesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MoviesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var binding: FragmentMoviesBinding
    private lateinit var adapter: MoviesAdapter
    private lateinit var originalMovies: List<MoviesTop>
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentMoviesBinding.inflate(inflater, container, false)


        moviesTop()
        moviesNow()
        return binding.root
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun moviesTop(){
        binding.rvMovies.layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)

        val moviesAdapter= MoviesAdapter(emptyList())
        {
            navigationTop(it)

        }
        binding.rvMovies.adapter=moviesAdapter

        lifecycleScope.launch {
            val api_key=getString(R.string.api_key)
            val moviesTop= ApiClient.service.listTopMovies(api_key)
            moviesAdapter.movies=moviesTop.results
            moviesAdapter.notifyDataSetChanged()
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun moviesNow(){
        binding.rvMoviesNow.layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        val adapter=MovieAdapterNow(emptyList()){
            navigationNow(it)
        }
        binding.rvMoviesNow.adapter=adapter
        lifecycleScope.launch {
            val api_key=getString(R.string.api_key)
            val moviesNow= ApiClient.service.listNowMovies(api_key)
            adapter.moviesNow=moviesNow.results
            adapter.notifyDataSetChanged()
        }
    }
    private fun navigationTop(movie:MoviesTop){
        val i=Intent(context,DetailsActivity::class.java)
        //i.putExtra(DetailsActivity.Extra_title,movie.title)
        i.putExtra(DetailsActivity.extra_movie,movie)
        startActivity(i)
    }
    private fun navigationNow(movieNow:MoviesNow){
        val intent=Intent(context,DetailsActivity::class.java)
        intent.putExtra(DetailsActivity.extra_movieNow,movieNow)
        startActivity(intent)
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MoviesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MoviesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}