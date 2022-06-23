package org.d3if2123.kalkulatorkalori.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.d3if2123.kalkulatorkalori.model.Tentang
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://raw.githubusercontent.com/farisnazhirah/APImobpro/main/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ApiService {
    @GET("api.json")
    suspend fun getTentangAplikasi(): Tentang
}

object KaloriApi {
    val service: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    fun getLogoUrl(): String {
        return BASE_URL + "logoParis2.png"
    }
}

enum class ApiStatus { LOADING, SUCCESS, FAILED }