package com.example.testingapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testingapp.data.RetrofitInstance
import com.example.testingapp.domain.Answer
import com.example.testingapp.domain.Question
import com.example.testingapp.domain.UserAction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class QuizViewModel : ViewModel() {
    private val _questions = MutableStateFlow<List<Question>>(emptyList())
    val questions = _questions.asStateFlow()

    private val _currentQuestionIndex = MutableStateFlow(0)
    val currentQuestionIndex = _currentQuestionIndex.asStateFlow()

    private val _score = MutableStateFlow(0)
    val score = _score.asStateFlow()

    private val _isGameFinished = MutableStateFlow(false)
    val isGameFinished = _isGameFinished.asStateFlow()

    private val _showNextButton = MutableStateFlow(false)
    val showNextButton = _showNextButton.asStateFlow()

    private val _isCurrentQuestionAnswered = MutableStateFlow(false)

    private val _selectedAnswerId = MutableStateFlow<String?>(null)


    init {
        loadQuestions()
    }

    private fun loadQuestions() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getQuestions()
                _questions.value = response.questions
                _isCurrentQuestionAnswered.value = false
                _selectedAnswerId.value = null
            } catch (e: Exception) {
                println("Ошибка загрузки: $e")
            }
        }
    }

    fun submitAnswer(answer: Answer) {
        viewModelScope.launch {
            val currentQuestion = _questions.value[_currentQuestionIndex.value]
            try {
                RetrofitInstance.api.submitAnswer(
                    UserAction(
                        quize_id = "1",
                        question_id = currentQuestion.id,
                        answer_id = answer.id
                    )
                )


                _selectedAnswerId.value = answer.id

                if (answer.is_valid) {
                    _score.value += currentQuestion.point
                }
                _isCurrentQuestionAnswered.value = true
                _showNextButton.value = true
            } catch (e: Exception) {
                println("Ошибка: $e")
            }
        }
    }

    fun nextQuestion() {
        val nextIndex = _currentQuestionIndex.value + 1
        if (nextIndex < _questions.value.size) {
            _currentQuestionIndex.value = nextIndex
            _showNextButton.value = false
            _isCurrentQuestionAnswered.value = false
            _selectedAnswerId.value = null
        } else {
            _isGameFinished.value = true
        }
    }
}