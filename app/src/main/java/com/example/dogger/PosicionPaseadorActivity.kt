package com.example.dogger

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*

import kotlinx.android.synthetic.main.activity_posicion_paseador.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class PosicionPaseadorActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var service: ApiServiceDuenio

    private lateinit var cameraPosition:CameraPosition

    private lateinit var sharedPreference: SharedPreferences

    val zoomLevel = 15.0f //This goes up to 21

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posicion_paseador)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.205.109:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        this.service = retrofit.create(ApiServiceDuenio::class.java)

        updateButton.setOnClickListener {
            updateLocation()
        }

        this.sharedPreference =  getSharedPreferences("DOGGER-USER", Context.MODE_PRIVATE)
    }

    fun SharedPreferences.getDouble(key: String, default: Double) =
        java.lang.Double.longBitsToDouble(getLong(key, java.lang.Double.doubleToRawLongBits(default)))

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val location = LatLng(
            this.sharedPreference.getDouble("lat", Double.MIN_VALUE),
            this.sharedPreference.getDouble("lon", Double.MIN_VALUE)
//            -34.6037389,
//            -58.3815704
        )
        mMap.addMarker(MarkerOptions().position(location).title("Marker in Home").icon(
            BitmapDescriptorFactory.fromResource(R.drawable.iconhome)))

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoomLevel))
    }

    private fun updateLocation() {
        val paseadoruid = this.sharedPreference.getString("paseadoruid","defaultname")
        this.service.getPosition(paseadoruid).enqueue(
            object : Callback<GetPositionResponse> {
                override fun onResponse(
                    call: Call<GetPositionResponse>,
                    response: Response<GetPositionResponse>
                ) {
                    val getPositionResponse = response.body()
                    Log.i("[GET-POSITION-RESPONSE]", getPositionResponse.toString())

                    val location = getPositionResponse?.data?.first()?.location
                    val lat = location?.latitude
                    val lon = location?.longitude

                    val locationToUpdate = LatLng(lat as Double, lon as Double)

                    mMap.clear()

                    val homeLocation = LatLng(
                        sharedPreference.getDouble("lat", Double.MIN_VALUE),
                        sharedPreference.getDouble("lon", Double.MIN_VALUE)
                    )
                    mMap.addMarker(MarkerOptions().position(homeLocation).title("Marker in Home").icon(
                        BitmapDescriptorFactory.fromResource(R.drawable.iconhome)))

                    mMap.addMarker(MarkerOptions().position(locationToUpdate).title("Last Location").icon(
                        BitmapDescriptorFactory.fromResource(R.drawable.dogwalker)))

                    cameraPosition = CameraPosition.Builder()
                        .target(locationToUpdate)
                        .zoom(zoomLevel).build()
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
                }

                override fun onFailure(call: Call<GetPositionResponse>, t: Throwable) {
                    Log.i("[GET-POSITION-ERROR]",t.message.toString())
                }
            }
        )
    }

    override fun onPause() {
        super.onPause()

        val paseadoruid = this.sharedPreference.getString("paseadoruid","default")
        this.service.tracing(FollowRequest(paseadoruid, false)).enqueue(
            object : Callback<FollowResponse> {
                override fun onResponse(
                    call: Call<FollowResponse>,
                    response: Response<FollowResponse>
                ) {
                    val FollowResponse = response.body()
                    Log.i("[FOLLOW-RESPONSE]", FollowResponse.toString())
                }

                override fun onFailure(call: Call<FollowResponse>, t: Throwable) {
                    Log.i("[FOLLOW-ERROR]", t.message.toString())
                }
            }
        )
    }


}