package com.example.dogger
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiServiceDuenio{
    @GET("/locations")
    fun getPosition(@Query("id_user") id_user: String): Call<GetPositionResponse>

    @POST("/tracing/")
    fun tracing(@Body followRequest: FollowRequest): Call<FollowResponse>
}

class GetPositionResponse(
    var data: List<UserLocation>
)

class UserLocation(
    var id_user: String,
    var location: Location
)

class Location(
    var longitude: Double,
    var latitude: Double
)

class FollowRequest(
    var id_user: String,
    var follow: Boolean
)

class FollowResponse(
    var user: String
)