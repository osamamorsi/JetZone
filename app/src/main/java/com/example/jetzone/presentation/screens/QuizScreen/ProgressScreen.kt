package com.example.jetzone.presentation.screens.QuizScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.NavigateNext
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jetzone.presentation.viewModels.quizViewmodel.QuizViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun ProgressScreen(viewModel: QuizViewModel, navController: NavController) {
    val coroutineScope = rememberCoroutineScope()
    val questions = viewModel.questions
    val currentIndex = viewModel.currentIndex
    val allAnswered = viewModel.questions.all { it.isAnswered }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(questions) { question ->
                val index = questions.indexOf(question)
                val isClickable = question.isAnswered || index == currentIndex

                val bgColor = when {
                    question.isAnswered && question.isCorrect == false -> MaterialTheme.colorScheme.error.copy(alpha = 0.2f)
                    else -> MaterialTheme.colorScheme.surface
                }

                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .graphicsLayer {
                            alpha = if (isClickable) 1f else 0.4f
                        }
                        .clip(RoundedCornerShape(8.dp))
                        .clickable(enabled = isClickable) {
                            viewModel.currentIndex = index
                            coroutineScope.launch {
                                delay(100)
                                navController.navigate("quiz") {
                                    popUpTo("quiz") { inclusive = true }
                                }
                            }
                        },
                    elevation = CardDefaults.cardElevation(4.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = bgColor
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "${question.id}",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

            }
        }

        Button(
            onClick = {
                viewModel.moveToNextQuestion()
                navController.navigate("quiz") {
                    popUpTo("quiz") { inclusive = true }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            enabled = !allAnswered
        ) {
            Text("Next Question")
            Icon(
                imageVector = Icons.AutoMirrored.Filled.NavigateNext,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}