package com.example.testingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import com.example.testingapp.presentation.QuizScreen
import com.example.testingapp.presentation.QuizViewModel

class MainActivity : ComponentActivity() {
    private val quizViewModel: QuizViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                QuizScreen(viewModel = quizViewModel)

            }
        }
    }
}

