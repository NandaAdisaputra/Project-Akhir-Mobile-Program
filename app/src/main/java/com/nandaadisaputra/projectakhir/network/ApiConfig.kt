package com.nandaadisaputra.projectakhir.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {

    fun getApiService(): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://shopher.000webhostapp.com/server/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ApiService::class.java)
    }
}