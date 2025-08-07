package com.example.jetzone.presentation.viewModels.quizViewmodel

import android.app.Application
import androidx.compose.runtime.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.jetzone.data.AppDatabase
import com.example.jetzone.data.Question
import kotlinx.coroutines.launch

class QuizViewModel(application: Application) : AndroidViewModel(application) {

    private val db = Room.databaseBuilder(
        application, AppDatabase::class.java, "quiz-db"
    ).build()

    private val _questions = mutableStateListOf<Question>()
    val questions: List<Question> = _questions
    var currentIndex by mutableIntStateOf(0)

    init {
        viewModelScope.launch {
            val loaded = db.questionDao().getAll()
            if (loaded.isEmpty()) {
                val qText = "What is this Jet?"
                val initial = listOf(
                    Question(1, qText, "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c4/F16_SCANG_InFlight.jpg/250px-F16_SCANG_InFlight.jpg", "F-16", listOf("F-16", "F/A-18", "SU-35", "Rafale")),
                    Question(2, qText, "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f8/IAF_Rafale_aircraft_touching_down_at_Air_Force_Station_Ambala_on_its_arrival_on_29_July_2020_%28cropped%29.jpg/250px-IAF_Rafale_aircraft_touching_down_at_Air_Force_Station_Ambala_on_its_arrival_on_29_July_2020_%28cropped%29.jpg", "Rafale", listOf("F-35", "Rafale", "F-22", "Typhoon")),
                    Question(3, qText, "https://upload.wikimedia.org/wikipedia/commons/thumb/7/7f/Su-35_%2812509727094%29.jpg/250px-Su-35_%2812509727094%29.jpg", "SU-35", listOf("SU-35", "F-16", "JF-17", "MiG-29")),
                    Question(4, qText, "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c3/F-22_Raptor_-_100702-F-4815G-217.jpg/1200px-F-22_Raptor_-_100702-F-4815G-217.jpg", "F-22", listOf("Typhoon", "F-22", "JAS 39", "F/A-18")),
                    Question(5, qText, "https://upload.wikimedia.org/wikipedia/commons/thumb/5/5d/Pakistan_JF-17_%28modified%29.jpg/1200px-Pakistan_JF-17_%28modified%29.jpg", "JF-17", listOf("JF-17", "F-35", "Gripen", "SU-57")),
                    Question(6, qText, "https://upload.wikimedia.org/wikipedia/commons/thumb/8/85/German_eurofighter.JPG/250px-German_eurofighter.JPG", "Typhoon", listOf("Typhoon", "Mirage 2000", "Tornado", "F-15")),
                    Question(7, qText, "https://upload.wikimedia.org/wikipedia/commons/thumb/0/0c/Russian_Air_Force%2C_747%2C_Mikoyan-Gurevich_MiG-29M2.jpg/330px-Russian_Air_Force%2C_747%2C_Mikoyan-Gurevich_MiG-29M2.jpg", "MiG-29", listOf("MiG-29", "SU-57", "Rafale", "JAS 39")),
                    Question(8, qText, "https://upload.wikimedia.org/wikipedia/commons/thumb/6/61/F-35A_flight_%28cropped%29.jpg/1200px-F-35A_flight_%28cropped%29.jpg", "F-35", listOf("F-35", "F/A-18", "SU-35", "Gripen")),
                    Question(9, qText, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR38EjUg7gsFBhrX5duqp7IA-hKblNP6gSw9A&s", "Gripen", listOf("Gripen", "JF-17", "F-15", "F-22")),
                    Question(10, qText, "https://upload.wikimedia.org/wikipedia/commons/thumb/6/60/USAF_F-15D_Top.jpg/250px-USAF_F-15D_Top.jpg", "F-15", listOf("F-15", "Typhoon", "MiG-29", "Mirage 2000")),
                    Question(11, qText, "https://upload.wikimedia.org/wikipedia/commons/thumb/8/8d/Sukhoi_Design_Bureau%2C_054%2C_Sukhoi_Su-57_%2849581306507%29.jpg/960px-Sukhoi_Design_Bureau%2C_054%2C_Sukhoi_Su-57_%2849581306507%29.jpg", "SU-57", listOf("SU-57", "F-16", "F-35", "Tornado")),
                    Question(12, qText, "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d1/Dassault_Mirage_2000-5_participating_in_Odyssey_Dawn_%28cropped%29.jpg/250px-Dassault_Mirage_2000-5_participating_in_Odyssey_Dawn_%28cropped%29.jpg", "Mirage 2000", listOf("Mirage 2000", "JAS 39", "Rafale", "F/A-18")),
                    Question(13, qText, "https://upload.wikimedia.org/wikipedia/commons/thumb/6/61/RAF_CONDUCTS_FIRST_AIR_STRIKES_OF_IRAQ_MISSION_MOD_45158635.jpg/250px-RAF_CONDUCTS_FIRST_AIR_STRIKES_OF_IRAQ_MISSION_MOD_45158635.jpg", "Tornado", listOf("Tornado", "F-22", "Gripen", "F-35")),
                    Question(14, qText, "https://upload.wikimedia.org/wikipedia/commons/thumb/7/77/Saab_JAS-39_Gripen_%2853079484003%29.jpg/250px-Saab_JAS-39_Gripen_%2853079484003%29.jpg", "JAS 39", listOf("JAS 39", "F-15", "SU-57", "Typhoon")),
                    Question(15, qText, "https://upload.wikimedia.org/wikipedia/commons/thumb/4/49/An_F-A-18C_Hornet_launches_from_the_flight_deck_of_the_conventionally_powered_aircraft_carrier.jpg/250px-An_F-A-18C_Hornet_launches_from_the_flight_deck_of_the_conventionally_powered_aircraft_carrier.jpg", "F/A-18", listOf("F/A-18", "MiG-29", "F-22", "F-16")),
                )
                db.questionDao().insertAll(initial)
                _questions.addAll(initial)
            } else {
                _questions.addAll(loaded)
            }

            // Set current index to first unanswered question
            currentIndex = _questions.indexOfFirst { !it.isAnswered }.takeIf { it != -1 } ?: 0
        }
    }

    fun submitAnswer(answer: String) {
        val question = _questions[currentIndex]
        val correct = answer.trim().equals(question.correctAnswer, ignoreCase = true)
        val updated = question.copy(
            userAnswer = answer,
            isAnswered = true,
            isCorrect = correct
        )
        _questions[currentIndex] = updated
        viewModelScope.launch {
            db.questionDao().updateQuestion(updated)
        }
    }

    fun moveToNextQuestion() {
        val nextIndex = _questions.indexOfFirst { !it.isAnswered }
        if (nextIndex != -1) {
            currentIndex = nextIndex
        } else {
            currentIndex = _questions.lastIndex
        }
    }

    fun goToNext() {
        if (currentIndex < _questions.size - 1) {
            currentIndex++
        }
    }
    fun resetProgress() {
        viewModelScope.launch {
            val resetQuestions = _questions.map { question ->
                question.copy(
                    isAnswered = false,
                    userAnswer = null,
                )
            }

            // Update database
            resetQuestions.forEach { db.questionDao().updateQuestion(it) }

            // Update local state
            _questions.clear()
            _questions.addAll(resetQuestions)

            // Reset current index
            currentIndex = 0
        }
    }

}