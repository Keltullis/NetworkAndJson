package com.bignerdranch.android.network_gson_exercise.sources.base

import com.google.gson.Gson
import okhttp3.OkHttpClient

// val baseUrl:String - общая часть всех запросов(т.е адресс,он по кд повторяется)
// client - класс из библиотеки okHttp, сам клиент который будет выполнять запросы
// парсер gson
class OkHttpConfig(
    val baseUrl:String,
    val client:OkHttpClient,
    val gson: Gson
)