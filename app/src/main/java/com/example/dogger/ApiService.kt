package com.example.dogger

import retrofit2.Call
import retrofit2.http.*

interface ApiService{
    @PUT("devices/{user}")
    fun registerDevice(@Path("user") id_user: Int, @Body registerRequest: RegisterRequest): Call<RegisterResponse>
}

class RegisterResponse(
    var message: String
)

class RegisterRequest(
    var id_device: String
)