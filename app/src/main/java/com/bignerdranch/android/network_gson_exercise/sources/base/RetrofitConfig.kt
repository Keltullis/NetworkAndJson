package com.bignerdranch.android.network_gson_exercise.sources.base

import com.squareup.moshi.Moshi
import retrofit2.Retrofit

/**
 * All stuffs required for making HTTP-requests with Retrofit client and
 * for parsing JSON-messages.
 */
// Здесь находится всё что нужно для сетевых запросов
class RetrofitConfig(
    val retrofit: Retrofit,
    val moshi: Moshi
)
