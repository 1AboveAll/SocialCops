package com.himanshurawat.myapplication.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.himanshurawat.myapplication.R
import kotlinx.android.synthetic.main.activity_splash.*

class Splash : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        var progressStatus = 0;
        val handler = Handler()


        Thread(Runnable {
            while (progressStatus < 100) {
                progressStatus += 1
                // Update the progress bar and display the
                //current value in the text view
                handler.post(Runnable {activity_splash_progress_bar.setProgress(progressStatus) })
                try {
                    // Sleep for 50 milliseconds.
                    Thread.sleep(50)
                    if (progressStatus == 100) {
                        startActivity(Intent(this@Splash, MainActivity::class.java))
                        finish()
                    }
                } catch (e: Throwable) {
                    e.printStackTrace()
                }

            }
        }).start()


    }
}