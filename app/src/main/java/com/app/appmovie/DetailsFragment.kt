package com.app.appmovie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.app.appmovie.DetailsActivity.Companion.extra_movie
import com.app.appmovie.DetailsActivity.Companion.extra_movieNow
import com.app.appmovie.databinding.FragmentDetailsBinding
import com.app.appmovie.models.MoviesTop
import com.app.appmovie.models.MoviesNow
import com.bumptech.glide.Glide

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailsFragment : Fragment() {
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

    private lateinit var binding: FragmentDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentDetailsBinding.inflate(inflater, container, false)

        val isTopMoviesSection = arguments?.getBoolean(ARG_IS_TOP_MOVIES_SECTION, true)

        binding.btnPlay.setOnClickListener { Toast.makeText(context,"Playing...",Toast.LENGTH_SHORT).show() }

        if (isTopMoviesSection == true) {
            moviesTop()
        } else {
            moviesNow()
        }
        return binding.root
    }

    private fun moviesTop(){
        val movie=requireActivity().intent.getParcelableExtra<MoviesTop>(extra_movie)
        binding.tvTitleDetail.text=movie?.title
        Glide.with(this).load("https://image.tmdb.org/t/p/w780/${movie?.backdrop_path}").into(binding.ivBackdrop)
        binding.tvResumen.text=movie?.overview
        binding.tvVoteAverge.text=movie?.vote_average.toString()
    }
    private fun moviesNow(){
        val moviesNow=requireActivity().intent.getParcelableExtra<MoviesNow>(extra_movieNow)
        binding.tvTitleDetail.text=moviesNow?.title
        Glide.with(this).load("https://image.tmdb.org/t/p/w780/${moviesNow?.backdrop_path}").into(binding.ivBackdrop)
        binding.tvResumen.text=moviesNow?.overview
        binding.tvVoteAverge.text=moviesNow?.vote_average.toString()
    }

    companion object {
    private const val ARG_IS_TOP_MOVIES_SECTION = "is_top_movies_section"
    fun newInstance(isTopMoviesSection: Boolean): DetailsFragment {
        val args = Bundle().apply {
            putBoolean(ARG_IS_TOP_MOVIES_SECTION, isTopMoviesSection)
        }
        val fragment = DetailsFragment()
        fragment.arguments = args
        return fragment

    }
    }
}