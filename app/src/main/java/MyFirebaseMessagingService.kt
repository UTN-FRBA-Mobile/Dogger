package com.example.dogger

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyFirebaseMessagingService : FirebaseMessagingService() {

    lateinit var service: ApiService
    lateinit var mFusedLocationClient: FusedLocationProviderClient

    override fun onCreate() {
        super.onCreate()
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.205.109:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        this.service = retrofit.create<ApiService>(ApiService::class.java)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "From: " + remoteMessage!!.from)
        updateLastLocation()
    }


    private fun updateLastLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mFusedLocationClient.lastLocation.addOnCompleteListener { task ->
                var location = task.result
                if (location != null) {
                    val sharedPreference = getSharedPreferences("DOGGER-USER", Context.MODE_PRIVATE)
                    service.updateLocation(
                        UpdateLocationRequest(
                            sharedPreference.getString("paseadoruid","default"),
                            location.latitude,
                            location.longitude
                        )
                    ).enqueue(
                        object : Callback<UpdateLocationResponse> {
                            override fun onResponse(
                                call: Call<UpdateLocationResponse>,
                                response: Response<UpdateLocationResponse>
                            ) {
                                val registerResponse = response.body()
                                Log.i("[UPDATELOC-RESPONSE]", registerResponse.toString())
                            }

                            override fun onFailure(
                                call: Call<UpdateLocationResponse>,
                                t: Throwable
                            ) {
                                Log.i("[UPDATELOC-ERROR]", t.message.toString())
                            }
                        }
                    )
                }
            }
        }
    }
}
