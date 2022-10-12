package com.bignerdranch.android.network_gson_exercise.sources.base

import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.suspendCancellableCoroutine
import com.bignerdranch.android.network_gson_exercise.app.model.AppException
import com.bignerdranch.android.network_gson_exercise.app.model.BackendException
import com.bignerdranch.android.network_gson_exercise.app.model.ConnectionException
import com.bignerdranch.android.network_gson_exercise.app.model.ParseBackendResponseException
import com.google.gson.Gson
import kotlinx.coroutines.CancellableContinuation
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * Base class for all OkHttp sources.
 */
// Упрощает работу с клиентом OkHttp и парсингом даных
open class BaseOkHttpSource(
    private val config: OkHttpConfig
) {
    // Чисто для удобства,что бы не писать config.gson и тд
    val gson:Gson = config.gson
    val client:OkHttpClient = config.client
    val contentType = "application/json; charset=utf-8".toMediaType()
    /**
     * Suspending function which wraps OkHttp [Call.enqueue] method for making
     * HTTP requests and wraps external exceptions into subclasses of [AppException].
     *
     * @throws ConnectionException
     * @throws BackendException
     * @throws ParseBackendResponseException
     */
    // Корутины
    suspend fun Call.suspendEnqueue(): Response {
        return suspendCancellableCoroutine { continuation ->
            continuation.invokeOnCancellation {
                // при отмене корутины,отменится сетевой запрос
                cancel()
            }
            enqueue(object :Callback{
                // если ошибка
                override fun onFailure(call: Call, e: IOException) {
                    val appException = ConnectionException(e)
                    continuation.resumeWithException(appException)
                }
                // если успех
                override fun onResponse(call: Call, response: Response) {
                    if(response.isSuccessful){
                        continuation.resume(response)
                    } else {
                        handleErrorResponse(response,continuation)
                    }
                }

            })
        }
    }

    private fun handleErrorResponse(response: Response,continuation: CancellableContinuation<Response>){
        val httpCode = response.code
        try {
            // тут будут 2 способа
            // в методе фром гсон указываем что хотим прочитать и во что превратить
            val map = gson.fromJson(response.body!!.string(),Map::class.java)
            // Читаем
            val message = map["error"].toString()
            // уведомляем корутину
            continuation.resumeWithException(BackendException(httpCode,message))
        } catch (e:Exception){
            // Если не удалось прочитать ответ об ошибке
            val appException = ParseBackendResponseException(e)
            continuation.resumeWithException(appException)
        }
    }

    /**
     * Concatenate the base URL with a path and query args.
     */
    // Метод объединяет строку запроса,что бы не писать полный путь к серверу в каждом запросе
    fun Request.Builder.endpoint(endpoint: String): Request.Builder {
        url("${config.baseUrl}$endpoint")
        return this
    }

    /**
     * Convert data class into [RequestBody] in JSON-format.
     */
    fun <T> T.toJsonRequestBody(): RequestBody {
        // Метод для любого типа данных,поэтому это дженерик(обобщённый метод),он превращает всё в gson
        // gson это google json,а json это просто json
        val json = gson.toJson(this)
        return json.toRequestBody(contentType)
    }

    /**
     * Parse OkHttp [Response] instance into data object. The type is derived from
     * TypeToken passed to this function as a second argument. Usually this method is
     * used to parse JSON arrays.
     *
     * @throws ParseBackendResponseException
     */
    // Для массивов
    fun <T> Response.parseJsonResponse(typeToken: TypeToken<T>): T {
        try {
            return gson.fromJson(this.body!!.string(),typeToken.type)
        } catch (e:Exception){
            throw ParseBackendResponseException(e)
        }
    }

    /**
     * Parse OkHttp [Response] instance into data object. The type is derived from
     * the generic type [T]. Usually this method is used to parse JSON objects.
     *
     * @throws ParseBackendResponseException
     */
    // Для объектов
    inline fun <reified T> Response.parseJsonResponse(): T {
        try {
            return gson.fromJson(this.body!!.string(),T::class.java)
        } catch (e:Exception){
            throw ParseBackendResponseException(e)
        }
    }

}