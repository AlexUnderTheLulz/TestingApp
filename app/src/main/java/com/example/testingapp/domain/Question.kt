package com.example.testingapp.domain

data class Question(
    val answers: List<Answer>,
    val id: String,
    val point: Int,
    val title: String
)