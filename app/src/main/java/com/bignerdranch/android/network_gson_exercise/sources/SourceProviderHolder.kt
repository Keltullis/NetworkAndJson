package com.bignerdranch.android.network_gson_exercise.sources

import com.bignerdranch.android.network_gson_exercise.app.Const
import com.bignerdranch.android.network_gson_exercise.app.Singletons
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import com.bignerdranch.android.network_gson_exercise.app.model.SourcesProvider
import com.bignerdranch.android.network_gson_exercise.app.model.settings.AppSettings
import com.bignerdranch.android.network_gson_exercise.sources.base.OkHttpConfig
import com.bignerdranch.android.network_gson_exercise.sources.base.OkHttpSourcesProvider
import com.google.gson.Gson
import okhttp3.logging.HttpLoggingInterceptor

object SourceProviderHolder {

    val sourcesProvider: SourcesProvider by lazy<SourcesProvider> {
        val config = OkHttpConfig(
            baseUrl = Const.BASE_URL,
            client = createOkHttpClient(),
            gson = Gson()
        )
        OkHttpSourcesProvider(config)
    }

    private fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(createAuthorizationInterceptor(Singletons.appSettings))
            .addInterceptor(createLoggingInterceptor())
            .build()
    }

    private fun createAuthorizationInterceptor(settings: AppSettings): Interceptor {
        return Interceptor { chain ->
            val newBuilder = chain.request().newBuilder()
            val token = settings.getCurrentToken()
            if(token != null){
                newBuilder.addHeader("Authorization",token)
            }
            return@Interceptor chain.proceed(newBuilder.build())
        }
    }

    private fun createLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

}