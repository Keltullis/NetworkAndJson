package com.bignerdranch.android.network_gson_exercise.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bignerdranch.android.network_gson_exercise.app.screens.splash.SplashFragment
import com.bignerdranch.android.network_gson_exercise.app.screens.splash.SplashViewModel

/**
 * Entry point of the app.
 *
 * Splash activity contains only window background, all other initialization logic is placed to
 * [SplashFragment] and [SplashViewModel].
 */
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Singletons.init(applicationContext)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

}