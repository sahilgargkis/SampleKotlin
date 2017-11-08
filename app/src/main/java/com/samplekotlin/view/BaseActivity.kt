package com.samplekotlin.view

import android.content.Intent

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import cn.pedant.SweetAlert.SweetAlertDialog
import com.samplekotlin.R
import com.samplekotlin.helper.callbacks.BaseCallback

/**
 * Created by krishnauser on 01-Nov-17.
 */
open class BaseActivity : AppCompatActivity() {

    lateinit var mInstance: BaseActivity
    private lateinit var alertDialog: SweetAlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mInstance = this
    }

    fun startIntent(activity: Class<*>) {
        startIntent(activity, null, null)
    }

    fun startIntent(activity: Class<*>, requestCode: Int) {
        startIntent(activity, null, requestCode)
    }

    fun startIntent(activity: Class<*>, bundle: Bundle?) {
        startIntent(activity, bundle, null)
    }

    fun startIntent(activity: Class<*>, bundle: Bundle?, requestCode: Int?) {
        val intent = Intent(mInstance, activity)
        if (bundle != null)
            intent.putExtras(bundle)
        if (requestCode != null)
            startActivityForResult(intent, requestCode)
        else
            startActivity(intent)
    }

    fun showToast(message: String) {
        Toast.makeText(mInstance, message, Toast.LENGTH_SHORT).show()
    }

    fun startLoading() {
        alertDialog = SweetAlertDialog(mInstance, SweetAlertDialog.PROGRESS_TYPE)
        alertDialog.setTitleText(getString(R.string.loading))
        alertDialog.show()
    }

    fun stopLoading() {
        if (alertDialog.isShowing)
            alertDialog.dismissWithAnimation()
    }

    fun showSuccessDialog(message: String) {
        showSuccessDialog(message, false)
    }

    fun showSuccessDialog(message: String, isFinish: Boolean) {
        SweetAlertDialog(mInstance, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(getString(R.string.success))
                .setContentText(message)
                .setConfirmClickListener { sweetAlertDialog ->
                    if (isFinish)
                        mInstance.finish()
                    sweetAlertDialog.dismissWithAnimation()
                }
                .show()
    }

    fun showInfoDialog(title: String, message: String) {
        SweetAlertDialog(mInstance, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText(title)
                .setContentText(message)
                .show()
    }

    fun showErrorDialog(message: String) {
        SweetAlertDialog(mInstance, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(getString(R.string.error))
                .setContentText(message)
                .show()
    }

    fun checkResponse(callback: BaseCallback): Boolean {
        return checkResponse(callback, true)
    }

    fun checkResponse(callback: BaseCallback, showError: Boolean): Boolean {
        if (callback.status == 1) {
            return true
        }
        if (showError)
            showErrorDialog(callback.message!!)
        return false
    }

}