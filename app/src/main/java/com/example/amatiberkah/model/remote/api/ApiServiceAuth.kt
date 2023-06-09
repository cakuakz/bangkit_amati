package com.example.amatiberkah.model.remote.api

import com.example.amatiberkah.model.remote.response.GeneralResponse
import com.example.amatiberkah.model.remote.response.LoginResponse
import com.example.amatiberkah.model.remote.response.RegisterResponse
import com.example.amatiberkah.model.remote.response.UserResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiServiceAuth {
    @POST("register-by-role")
    @FormUrlEncoded
    suspend  fun registerByRole (
        @Query("role") role: String,
        @Field("fullname") fullname: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("phoneNumber") phoneNumber: String,
        @Field("photo") photo: String?,
        @Field("provinceId") provinceId: String?,
        @Field("cityId") cityId: String?,
        @Field("districtId") districtId: String?,
        @Field("subDistrictId") subDistrictId: String?,
        @Field("postcode") postcode: String?,
        @Field("address") address: String?,
    ) : RegisterResponse

    @POST("login")
    @FormUrlEncoded
    suspend fun login (
        @Field("email") email: String,
        @Field("password") password: String
    ) : LoginResponse

}