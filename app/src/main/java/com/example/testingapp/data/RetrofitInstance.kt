package com.example.testingapp.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL  = "http://10.0.2.2:8000/"

    val api: QuizAPI by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuizAPI::class.java)
    }
}