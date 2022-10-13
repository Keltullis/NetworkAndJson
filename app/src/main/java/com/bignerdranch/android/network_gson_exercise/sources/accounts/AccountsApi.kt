package com.bignerdranch.android.network_gson_exercise.sources.accounts

import com.bignerdranch.android.network_gson_exercise.sources.accounts.entities.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface AccountsApi{

    @POST("sign-in")
    suspend fun signIn(@Body signInRequestEntity: SignInRequestEntity):SignInResponseEntity

    @POST("sign-up")
    suspend fun signUp(@Body body:SignUpRequestEntity)

    @GET("me")
    suspend fun getAccount():GetAccountResponseEntity

    @GET("me")
    suspend fun setUsername(@Body body: UpdateUsernameRequestEntity)

}