package com.dosplash.utils

import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class APIClient {
    companion object {
        fun getClient(): Retrofit {
            val httpClient = OkHttpClient.Builder()

            val client = httpClient.build()
            return Retrofit.Builder()
                .baseUrl("https://recipesapi.herokuapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }
    }
}
