package com.example.dogger

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

        simulateButton.setOnClickListener {
            callServerToSimulate()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val location = LatLng(-34.6037389, -58.3815704)
        mMap.addMarker(MarkerOptions().position(location).title("Marker in Obelisco"))

        val zoomLevel = 12.0f //This goes up to 21
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoomLevel))
    }

    private fun callServerToSimulate() {

        this.service.getPosition("jon").enqueue(
            object : Callback<GetPositionResponse> {
                override fun onResponse(
                    call: Call<GetPositionResponse>,
                    response: Response<GetPositionResponse>
                ) {
                    val getPositionResponse = response.body()
                    Log.i("[GET-POSITION-RESPONSE]", getPositionResponse.toString())

                    val lat = getPositionResponse?.data?.first()?.location?.latitude as Double
                    val lon = getPositionResponse.data.first().location.longitude

                    val location = LatLng(lat, lon)

                    mMap.clear()

                    mMap.addMarker(MarkerOptions().position(location).title("Last Location").icon(
                        BitmapDescriptorFactory.fromResource(R.drawable.dogwalker)))

                    val zoomLevel = 12.0f //This goes up to 21
                    cameraPosition = CameraPosition.Builder()
                        .target(location)
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
        this.service.tracing(FollowRequest("jon", false)).enqueue(
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