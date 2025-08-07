package com.example.jetzone.presentation.screens.QuizScreen

import android.media.MediaPlayer
import androidx.compose.animation.*
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.jetzone.R
import com.example.jetzone.presentation.viewModels.quizViewmodel.QuizViewModel

@Composable
fun QuizScreen(viewModel: QuizViewModel, navController: NavController) {
    val context = LocalContext.current

    val correctSound = remember(context) { MediaPlayer.create(context, R.raw.correct) }
    val wrongSound = remember(context) { MediaPlayer.create(context, R.raw.wrong) }

    DisposableEffect(Unit) {
        onDispose {
            correctSound.release()
            wrongSound.release()
        }
    }

    val question = viewModel.questions.getOrNull(viewModel.currentIndex)
    var answerResultShown by remember(question?.id) { mutableStateOf(question?.isAnswered == true) }

    if (question != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text(
                text = "Question ${viewModel.currentIndex + 1} of ${viewModel.questions.size}",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 16.dp)
            )

            Image(
                painter = rememberAsyncImagePainter(question.imageUrl),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color.LightGray)
            )

            Text(
                text = question.text,
                modifier = Modifier.padding(vertical = 15.dp),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleMedium
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 400.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                userScrollEnabled = false
            ) {
                items(question.choices) { choice ->
                    val isCorrect = choice == question.correctAnswer
                    val isSelected = choice == question.userAnswer
                    val showColors = question.isAnswered

                    val backgroundColor = when {
                        showColors && isCorrect -> Color(0xFFD0F0C0) // Green
                        showColors && isSelected && !isCorrect -> Color(0xFFFFC0CB) // Red
                        else -> MaterialTheme.colorScheme.surface
                    }

                    var buttonScale by remember { mutableStateOf(1f) }

                    LaunchedEffect(isSelected) {
                        if (isSelected) {
                            buttonScale = 1.1f
                            kotlinx.coroutines.delay(150)
                            buttonScale = 1f
                        }
                    }

                    Button(
                        onClick = {
                            if (!question.isAnswered) {
                                viewModel.submitAnswer(choice)
                                answerResultShown = true
                                if (isCorrect) correctSound.start() else wrongSound.start()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .scale(buttonScale),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = backgroundColor,
                            contentColor = MaterialTheme.colorScheme.onSurface
                        ),
                        enabled = !question.isAnswered
                    ) {
                        Text(choice, maxLines = 2)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (question.isAnswered && answerResultShown) {
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn(animationSpec = tween(300)) + scaleIn(initialScale = 0.8f, animationSpec = spring()),
                    exit = fadeOut(animationSpec = tween(300)) + scaleOut(targetScale = 0.8f)
                ) {
                    val isCorrect = question.userAnswer == question.correctAnswer
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(
                                if (isCorrect) Color(0xFFD0F0C0)
                                else Color(0xFFFFC0CB)
                            )
                            .padding(16.dp)
                    ) {
                        Text(
                            if (isCorrect) "✅ Correct! - Answer: ${question.correctAnswer}"
                            else "❌ Wrong! - Correct Answer: ${question.correctAnswer}",
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            val isLastQuestion = viewModel.currentIndex == viewModel.questions.lastIndex
            if (question.isAnswered && !isLastQuestion) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        answerResultShown = false
                        viewModel.goToNext()
                    }
                ) {
                    Text("Next")
                }
            }
        }
    }
}