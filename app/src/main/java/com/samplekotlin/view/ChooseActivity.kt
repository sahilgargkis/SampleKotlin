package com.samplekotlin.view

import android.os.Bundle
import butterknife.ButterKnife
import butterknife.OnClick
import com.samplekotlin.R

class ChooseActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose)

        ButterKnife.bind(this)
    }

    @OnClick(R.id.btnLogin)
    fun login() {
        startIntent(LoginActivity::class.java)
    }

    @OnClick(R.id.btnSignUp)
    fun signUp() {
        startIntent(SignUpActivity::class.java)
    }
}
