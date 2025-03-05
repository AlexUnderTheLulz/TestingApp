package com.example.testingapp.data

import com.example.testingapp.domain.UserAction
import com.example.testingapp.domain.quizModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface QuizAPI {
    @GET("quize/1/questions?amount=3")
    suspend fun getQuestions(): quizModel

    @POST("user/action")
    suspend fun submitAnswer(@Body action: UserAction): Response<Unit>
}