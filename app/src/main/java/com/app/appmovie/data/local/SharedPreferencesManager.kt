package com.app.appmovie.data.local

import android.content.Context
import android.content.SharedPreferences
import com.app.appmovie.models.MoviesTop

class SharedPreferencesManager(private val context: Context) {

    companion object {
        private const val PREFS_NAME = "movie_prefs"
        private const val PREF_PREFIX_KEY = "movie_"
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun cacheMovie(movie: MoviesTop) {
        val editor = sharedPreferences.edit()
        editor.putString("$PREF_PREFIX_KEY${movie.id}_title", movie.title)
        editor.putString("$PREF_PREFIX_KEY${movie.id}_image", movie.poster_path)
        editor.putFloat("$PREF_PREFIX_KEY${movie.id}_rating", movie.vote_average.toFloat())
        editor.apply()
    }

    fun getCachedMovie(movieId: Int): MoviesTop? {
        val title = sharedPreferences.getString("${PREF_PREFIX_KEY}${movieId}_title", null)
        val image = sharedPreferences.getString("${PREF_PREFIX_KEY}${movieId}_image", null)
        val rating = sharedPreferences.getFloat("${PREF_PREFIX_KEY}${movieId}_rating", 0f)
        return if (title != null && image != null) {
            MoviesTop(false, "", emptyList(), movieId, "", "", "", 0.0, image, "", title, false, rating.toDouble(), 0)
        } else {
            null
        }
    }
}