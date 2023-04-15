package com.app.appmovie
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


class ActivityMovie : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val myFragment = MoviesFragment()
        fragmentTransaction.add(R.id.container, myFragment)
        fragmentTransaction.commit()

    }
}