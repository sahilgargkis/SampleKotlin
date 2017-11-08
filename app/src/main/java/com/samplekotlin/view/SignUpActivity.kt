package com.samplekotlin.view

import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.text.TextUtils
import android.util.Patterns
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.location.places.AutocompleteFilter
import com.google.android.gms.location.places.PlaceBuffer
import com.google.android.gms.location.places.Places
import com.google.android.gms.maps.model.LatLng
import com.samplekotlin.R
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.include_toolbar.*

class SignUpActivity : BaseActivity(), GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private lateinit var mGoogleApiClient: GoogleApiClient

    private lateinit var mAdapter: PlaceAutocompleteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        toolbar.setTitle(R.string.createprofile)

        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mGoogleApiClient = GoogleApiClient.Builder(mInstance)
                .enableAutoManage(mInstance, 0, this)
                .addApi(Places.GEO_DATA_API)
                .build()

        etLocation.setOnItemClickListener(mAutocompleteClickListener)

        val typeFilter = AutocompleteFilter.Builder()
                .setCountry("IN")
                .build()

        mAdapter = PlaceAutocompleteAdapter(mInstance, mGoogleApiClient, null, typeFilter)
        etLocation.setAdapter(mAdapter)

        btnGetStarted.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            btnGetStarted.id -> {
                val firstName = etFirstName.text.toString().trim()
                val lastName = etLastName.text.toString().trim()
                val email = etEmail.text.toString().trim()
                val password = etPass.text.toString().trim()
                val rePassword = etrepass.text.toString().trim()
                val location = etLocation.text.toString().trim()
                val taskType =
                        if (chkPostTask.isChecked && chkCompleteTask.isChecked) "2"
                        else if (chkPostTask.isChecked) "0"
                        else if (chkCompleteTask.isChecked) "1"
                        else ""

                val locationPlace = etLocation.tag as LatLng?

                if (TextUtils.isEmpty(firstName)) {
                    showToast("Enter First Name")
                } else if (TextUtils.isEmpty(lastName)) {
                    showToast("Enter Last Name")
                } else if (TextUtils.isEmpty(email)) {
                    showToast("Enter Email")
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    showToast("Enter valid Email")
                } else if (/*!isFacebookLogin && */TextUtils.isEmpty(password)) {
                    showToast("Enter Password")
                } else if (/*!isFacebookLogin && */TextUtils.isEmpty(rePassword)) {
                    showToast("Enter Re-Password")
                } else if (/*!isFacebookLogin && */!TextUtils.equals(password, rePassword)) {
                    showToast("Password didn't Match")
                } else if (TextUtils.isEmpty(taskType)) {
                    showToast("Select type of task")
                } else if (TextUtils.isEmpty(location) || etLocation.tag == null) {
                    showToast("Select valid Location")
                } else {
                    startIntent(HomeActivity::class.java)
                    ActivityCompat.finishAffinity(mInstance)
                }
            }
        }
    }

    private val mAutocompleteClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
        val item = mAdapter.getItem(position)
        val placeId = item?.getPlaceId()

        val placeResult = Places.GeoDataApi.getPlaceById(mGoogleApiClient, placeId)
        placeResult.setResultCallback(mUpdatePlaceDetailsCallback)
    }

    private val mUpdatePlaceDetailsCallback = ResultCallback<PlaceBuffer> { places ->
        if (!places.status.isSuccess) {
            places.release()
            return@ResultCallback
        }
        val place = places.get(0)
        etLocation.tag = place.latLng

        places.release()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
            else -> return false
        }
        return true
    }

    override fun onConnectionFailed(p0: ConnectionResult) {

    }

}
