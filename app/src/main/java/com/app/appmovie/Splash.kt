package com.app.appmovie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import com.app.appmovie.databinding.ActivitySplashBinding
import java.util.*

class Splash : AppCompatActivity() {
    var count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding=ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pB = findViewById<ProgressBar>(R.id.pB)
        binding.pB
        //Tiempo del ProgresBar
        val timerProgres = Timer()
        val timerTask: TimerTask = object : TimerTask() {
            override fun run() {
                count++
                pB.progress = count
                if (count == 30) {
                    timerProgres.cancel()
                    val intent = Intent(applicationContext, ActivityMovie::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
        //-ADJG
        val time = Timer()
        time.schedule(timerTask, 0, 80)
    }
}