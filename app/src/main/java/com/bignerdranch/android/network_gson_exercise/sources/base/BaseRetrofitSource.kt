package com.bignerdranch.android.network_gson_exercise.sources.base

import com.bignerdranch.android.network_gson_exercise.app.model.BackendException
import com.bignerdranch.android.network_gson_exercise.app.model.ConnectionException
import com.bignerdranch.android.network_gson_exercise.app.model.ParseBackendResponseException
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonEncodingException
import okio.IOException
import retrofit2.HttpException

// todo #4: add property for accessing Retrofit instance and implement
//          wrapRetrofitExceptions() method.
open class BaseRetrofitSource(
    retrofitConfig: RetrofitConfig
) {

    val retrofit = retrofitConfig.retrofit

    private val errorAdapter = retrofitConfig.moshi.adapter(ErrorResponseBody::class.java)

    /**
     * Map network and parse exceptions into in-app exceptions.
     * @throws BackendException
     * @throws ParseBackendResponseException
     * @throws ConnectionException
     */
    // Преобразователь исключений во внутренние
    suspend fun <T> wrapRetrofitExceptions(block: suspend () -> T): T {
        return try {
            block()
        } catch (e:JsonDataException){
            //Это ошибки парсера moshi
            throw ParseBackendResponseException(e)
        } catch (e:JsonEncodingException){
            throw ParseBackendResponseException(e)
        } catch (e:HttpException){
            //Код ответа не успешный,но ответ успешно прочитан
            throw createBackendException(e)
        } catch (e:IOException){
            throw ConnectionException(e)
        }
    }

    private fun createBackendException(e:HttpException):Exception{
       return try {
           val errorBody = errorAdapter.fromJson(e.response()!!.errorBody()!!.string())!!
           BackendException(e.code(),errorBody.error)
       } catch (e:Exception){
           throw ParseBackendResponseException(e)
       }
    }

    class ErrorResponseBody(
        val error:String
    )

}