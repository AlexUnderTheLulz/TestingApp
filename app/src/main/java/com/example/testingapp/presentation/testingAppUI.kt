package com.example.testingapp.presentation


import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testingapp.domain.Answer


@Composable
fun QuizScreen(viewModel: QuizViewModel) {
    val questions by viewModel.questions.collectAsState()
    val currentIndex by viewModel.currentQuestionIndex.collectAsState()
    val score by viewModel.score.collectAsState()
    val isGameFinished by viewModel.isGameFinished.collectAsState()
    val showNextButton by viewModel.showNextButton.collectAsState()


    if (isGameFinished) {
        GameFinishedScreen(score)
        return
    }

    if (questions.isEmpty()) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(50.dp)
        )
        return
    }

    val currentQuestion = questions[currentIndex]

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .align(Alignment.Center)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = currentQuestion.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontSize = 20.sp
            )

            currentQuestion.answers.forEach { answer ->
                AnswerButton(
                    answer = answer,
                    enabled = !showNextButton,
                    onClick = { viewModel.submitAnswer(answer) }
                )
            }

            if (showNextButton) {
                NextButton(onClick = { viewModel.nextQuestion() })
            }
        }

        Text(
            text = "Текущий счёт: $score",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            color = Color.Black,
            fontSize = 16.sp
        )
    }
}

@Composable
fun AnswerButton(
    answer: Answer,
    enabled: Boolean,
    onClick: () -> Unit
) {

    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(10.dp))
            .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(10.dp)),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White
        )
    ) {
        Text(
            text = answer.text,
            color = Color.Black,
            modifier = Modifier.padding(vertical = 12.dp),
            fontSize = 16.sp
        )
    }
}

@Composable
fun NextButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(10.dp))
            .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(10.dp)),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White
        )
    ) {
        Text(
            text = "Следующий вопрос",
            color = Color.Black,
            modifier = Modifier.padding(vertical = 12.dp),
            fontSize = 16.sp
        )
    }
}

@Composable
fun GameFinishedScreen(score: Int) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "\nВаш счёт: $score",
            style = MaterialTheme.typography.h4,
            textAlign = TextAlign.Center,
            color = Color.Black
        )
    }
}