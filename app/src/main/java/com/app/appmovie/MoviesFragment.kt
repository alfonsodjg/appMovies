package com.app.appmovie

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.appmovie.adapter.MovieAdapterNow
import com.app.appmovie.adapter.MoviesAdapter
import com.app.appmovie.data.local.SharedPreferencesManager
import com.app.appmovie.databinding.FragmentMoviesBinding
import com.app.appmovie.models.MoviesTop
import com.app.appmovie.models.MoviesNow
import com.app.appmovie.data.remote.ApiClient
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
    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var sharedPreferencesManager: SharedPreferencesManager
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentMoviesBinding.inflate(inflater, container, false)

        sharedPreferencesManager=SharedPreferencesManager(requireContext())
        if(context?.let { checkConnection(it) } == true){
            moviesTop()
            moviesNow()
            //SearchView con su evento que escucha cuando se escribe dentro de ella
            binding.svMovies.isFocusable=false//funcion que hace que el teclado no se inicialice automaticamente
            binding.svMovies.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(p0: String): Boolean {
                    filterMoviesTop(p0)
                    filterMoviesNow(p0)
                    return true
                }
            })
        }else{
            binding.tvMoviesTop.text="Error network not adviable"
            binding.tvMoviesTop.gravity=Gravity.CENTER
            binding.tvMoviesNow.visibility=View.GONE
            binding.rvMovies.visibility=View.GONE
            binding.rvMoviesNow.visibility=View.GONE
            Toast.makeText(context,"Error, virifica conexion a internet cierre y vuelva abrir la aplicacion",Toast.LENGTH_LONG).show()
        }

        return binding.root
    }
    //Metodo que inserta las peliculas dentro del recyclerview para peliculas top o mejores calificadas
    @SuppressLint("NotifyDataSetChanged")
    private fun moviesTop(){
        binding.rvMovies.layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)

         moviesAdapter= MoviesAdapter(emptyList())
        {
            navigationTop(it)

        }
        binding.rvMovies.adapter=moviesAdapter
        if(context?.let { checkConnection(it) }==true){
            lifecycleScope.launch {
                val api_key=getString(R.string.api_key)
                val moviesTop= ApiClient.service.listTopMovies(api_key)
                moviesAdapter.movies=moviesTop.results
                moviesAdapter.filteredList=moviesTop.results
                moviesTop.results.forEach { movie -> sharedPreferencesManager.cacheMovie(movie) }
                moviesAdapter.notifyDataSetChanged()
            }
        }else{
            val movieId=3
            sharedPreferencesManager=SharedPreferencesManager(requireContext())
            val cachedMovies = sharedPreferencesManager.getCachedMovie(movieId)
            binding.rvMoviesNow.visibility=View.GONE
            if (cachedMovies != null) {
                moviesAdapter.movies = listOf(cachedMovies)
                moviesAdapter.filteredList = listOf(cachedMovies)
                moviesAdapter.notifyDataSetChanged()
                Toast.makeText(context,"Error network",Toast.LENGTH_SHORT).show()
            } else {
                sharedPreferencesManager=SharedPreferencesManager(requireContext())
                Toast.makeText(context,"Movies not found",Toast.LENGTH_SHORT).show()
            }
        }

    }
    //Metodo que inserta las peliculas dentro del recyclerview now-playing
    @SuppressLint("NotifyDataSetChanged")
    private fun moviesNow(){
        binding.rvMoviesNow.layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        val adapter=MovieAdapterNow(emptyList()){
            navigationNow(it)
        }
        binding.rvMoviesNow.adapter=adapter

        if(context?.let { checkConnection(it) } == true){

        }

        lifecycleScope.launch {
            val api_key=getString(R.string.api_key)
            val moviesNow= ApiClient.service.listNowMovies(api_key)
            adapter.moviesNow=moviesNow.results
            adapter.filerterMoviesNow=moviesNow.results
            adapter.notifyDataSetChanged()
        }
    }
    //Metodo que filtra las peliculas mejores calificadas o peliculas top
    @SuppressLint("NotifyDataSetChanged")
    private fun filterMoviesTop(text:String){
        val adapterTop=binding.rvMovies.adapter as MoviesAdapter?
        adapterTop?.filter?.filter(text)
        adapterTop?.notifyDataSetChanged()
    }
    //Metodo que filtra las peliculas now-playing
    @SuppressLint("NotifyDataSetChanged")
    private fun filterMoviesNow(text: String){
        val adapterNow=binding.rvMoviesNow.adapter as MovieAdapterNow?
        adapterNow?.filter?.filter(text)
        adapterNow?.notifyDataSetChanged()
    }
    //Metodo que va a la otra actividad y nos envia las peliculas mejor calificadas o peliculas top
    private fun navigationTop(movie:MoviesTop){
        val i=Intent(context,DetailsActivity::class.java)
        //i.putExtra(DetailsActivity.Extra_title,movie.title)
        i.putExtra(DetailsActivity.extra_movie,movie)
        startActivity(i)
    }
    //Metodo que va a la siguiente actividad y nos envia la informacion de las peliculas now-playing
    private fun navigationNow(movieNow:MoviesNow){
        val intent=Intent(context,DetailsActivity::class.java)
        intent.putExtra(DetailsActivity.extra_movieNow,movieNow)
        startActivity(intent)
    }
    //Medoto que verifica la conexion a internet
    fun checkConnection(context: Context):Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
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