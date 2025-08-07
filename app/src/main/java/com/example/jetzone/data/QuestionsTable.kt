package com.example.jetzone.data

import androidx.room.TypeConverter
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questions")
data class Question(
    @PrimaryKey val id: Int,
    val text: String,
    val imageUrl: String,
    val correctAnswer: String,
    val choices: List<String>,
    val userAnswer: String? = null,
    val isAnswered: Boolean = false,
    val isCorrect: Boolean = false
)

class Converters {
    @TypeConverter
    fun fromList(value: List<String>): String = value.joinToString(",")

    @TypeConverter
    fun toList(value: String): List<String> = value.split(",")
}