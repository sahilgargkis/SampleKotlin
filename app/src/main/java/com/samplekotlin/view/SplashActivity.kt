package com.samplekotlin.view

import android.os.Bundle
import android.os.Handler
import butterknife.ButterKnife
import com.samplekotlin.R

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        ButterKnife.bind(this)

        Handler().postDelayed(Runnable {
            startIntent(ChooseActivity::class.java)
            finish()
        }, 3000)
    }

}
