package com.example.dogger

import retrofit2.Call
import retrofit2.http.*

interface ApiService{
    @PUT("devices/{user}")
    fun registerDevice(@Path("user") id_user: String, @Body registerRequest: RegisterRequest): Call<RegisterResponse>

    @POST("/locations/")
    fun updateLocation(@Body updateLocationRequest: UpdateLocationRequest): Call<UpdateLocationResponse>
}

class RegisterResponse(
    var message: String
)

class RegisterRequest(
    var id_device: String
)

class UpdateLocationRequest(
    var id_user: String,
    var latitude: Double,
    var longitude: Double
)

class UpdateLocationResponse(
    var message: String
)