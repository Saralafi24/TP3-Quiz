package com.example.quizimages

import androidx.annotation.DrawableRes

/**
 * Représente une question du quiz.
 *
 * @property texte        énoncé de la question
 * @property imageRes     identifiant de l'image dans res/drawable (ex. R.drawable.triangle)
 * @property choix        exactement trois réponses possibles
 * @property bonneReponse indice (0, 1 ou 2) du choix correct dans [choix]
 * @property description  description de l'image pour l'accessibilité
 */
data class Question(
    val texte: String,
    @DrawableRes val imageRes: Int,
    val choix: Array<String>,
    val bonneReponse: Int,
    val description: String
)
