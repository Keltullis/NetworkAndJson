package com.bignerdranch.android.network_gson_exercise

import com.squareup.moshi.Moshi
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.POST


// Тело запроса
data class SignInRequestBodyRetr(
    val email:String,
    val password:String
)

// Тело ответа от сервера
data class SignInResponseBodyRetr(
    val token:String
)

// Фишка ретрофита,не нужно писать реализацию запроса и ответа,нужно описать интерфейс
// запрос с методом пост,внутри сам запрос,без url,он будет дальше
// аннотация @Body сконвертирует класс в тело запроса,в конце идёт тип ответа
// ретрофит автоматически прочитает ответ от сервера и попытается преобразовать его в объект этого класса
// если нужно добавить аргументы,то используется аннотация @Query
interface Api{
    @POST("sign-in")
    suspend fun signIn(@Body body:SignInRequestBodyRetr):SignInResponseBodyRetr
}

fun main() = runBlocking{
    // Создаём клиент ретрофита

    // Перехватчик запроса
    val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    // Клиент okHttp
    val client = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
    // Парсер moshi
    val moshi = Moshi.Builder().build()
    // Адаптер между moshi и retrofit,он нужен для того что бы ретрофит мог парсить json
    val moshiConverterFactory = MoshiConverterFactory.create(moshi)
    // Сам ретрофит
    val retrofit = Retrofit.Builder()
        .baseUrl("http://127.0.0.1:12345")
        .client(client)
        .addConverterFactory(moshiConverterFactory)
        .build()

    // Заставляем этот экземляра клиента ретрофита,написать реализацию интерфейса Api
    val api:Api = retrofit.create(Api::class.java)

    // Формируем тело запроса
    val requestBody = SignInRequestBodyRetr(
        email = "admin@google.com",
        password = "123"
    )
    // Отправляем запрос на сервер(во всём остальном проекте,пишется запрос и всё,всё что выше пишется 1 раз)
    val response = api.signIn(requestBody)
    println("TOKEN: ${response.token}")
}