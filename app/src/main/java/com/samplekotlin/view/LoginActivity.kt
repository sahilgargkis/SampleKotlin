package com.samplekotlin.view

import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import com.samplekotlin.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            btnLogin.id -> {
                val email = etEmail.text.toString().trim()
                val password = etPass.text.toString().trim()

                if (TextUtils.isEmpty(email)) {
                    showToast("Enter Email")
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    showToast("Enter valid Email")
                } else if (TextUtils.isEmpty(password)) {
                    showToast("Enter Password")
                } else {
                    startIntent(HomeActivity::class.java)
                    ActivityCompat.finishAffinity(mInstance)
                }
            }
        }
    }
}
