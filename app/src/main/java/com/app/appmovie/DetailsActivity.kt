package com.app.appmovie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.appmovie.databinding.ActivityDetailsBinding
import com.app.appmovie.models.MoviesTop
import com.app.appmovie.models.MoviesNow

class DetailsActivity : AppCompatActivity() {

    companion object{
        const val extra_movie="DetailsActivity:movie"
        const val extra_movieNow="DetailsActivity:movieNow"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding=ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener {back()}
        movie()
    }
    private fun movie(){
        val isTopMoviesSection = intent.getBooleanExtra("is_top_movies_section", true)
        val fragmentDetails = DetailsFragment.newInstance(isTopMoviesSection)

        if(intent.getParcelableExtra<MoviesTop>(extra_movie) != null) {
            fragmentDetails.arguments = Bundle().apply {
                putParcelable(extra_movie, intent.getParcelableExtra<MoviesTop>(extra_movie))
                putBoolean("is_top_movies_section", true)
            }
        } else if(intent.getParcelableExtra<MoviesNow>(extra_movieNow) != null){
            fragmentDetails.arguments = Bundle().apply {
                putParcelable(extra_movieNow, intent.getParcelableExtra<MoviesNow>(extra_movieNow))
                putBoolean("is_top_movies_section", false)
            }
        }
        val fragmentManager=supportFragmentManager
        val fragmentTransaccion=fragmentManager.beginTransaction()
        fragmentTransaccion.add(R.id.container_details,fragmentDetails)
        fragmentTransaccion.commit()
    }

    private fun back(){
        val i=Intent(this,ActivityMovie::class.java)
        startActivity(i)
        finish()
    }

}