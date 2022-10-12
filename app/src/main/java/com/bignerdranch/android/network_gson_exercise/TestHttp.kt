package com.bignerdranch.android.network_gson_exercise

import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor

data class SignInRequestBody(val email:String, val password:String)

data class SignInResponseBody(val token:String)

val contentType = "application/json; charset=utf-8".toMediaType()

fun main(){
    // Реализуем отправку запроса и чтение результата

    // Логирование запросов(перехватчик запросов,добавляем его для удобства)
    val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    // Парсер json сообщений,потому что сервер принимает gson и отправляет gson
    val gson = Gson()

    // Создаём клиент
    //val client = OkHttpClient()
    val client = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

    //----- Тело запроса в gson
    val requestBody = SignInRequestBody(
        email = "admin@google.com",
        password = "123"
    )
    val requestBodyString = gson.toJson(requestBody)
    val okHttpRequestBody = requestBodyString.toRequestBody(contentType)

    //----- Создаём шаблон запроса(метод запроса POST)
    val request = Request.Builder()
        .post(okHttpRequestBody)
        .url("http://127.0.0.1:12345/sign-in")
        .build()

    // Имея request можем создать вызов запроса call,который и отправит запрос на сервер
    val call = client.newCall(request)

    // Выполняем запрос Синхронно(текущий поток заблочится) или асинхронно
    val response = call.execute()
    // Проверяем респонс на успех
    if(response.isSuccessful){
        val responseBodyString = response.body!!.string()
        // парсим gson(строка ответа от сервера и класс в который нужно превратить эту строку)
        val signInResponseBody = gson.fromJson(responseBodyString,SignInResponseBody::class.java)
        println("TOKEN: ${signInResponseBody.token}")
    } else {
        throw IllegalStateException("Oops")
    }
}