package com.example.quizimages

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.quizimages.databinding.ActivityMainBinding

/**
 * Unique activité du quiz en images (thème : formes géométriques).
 * 5 questions statiques, 3 boutons de choix par question.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Les cinq questions statiques. La bonne réponse n'est pas toujours au même endroit.
    private val questions = arrayOf(
        Question(
            "Quelle est cette forme ?", R.drawable.triangle,
            arrayOf("Carré", "Triangle", "Cercle"), 1, "Un triangle rouge"
        ),
        Question(
            "Combien de côtés égaux cette forme possède-t-elle ?", R.drawable.carre,
            arrayOf("Quatre", "Trois", "Aucun"), 0, "Un carré bleu"
        ),
        Question(
            "Quelle forme n'a aucun angle ?", R.drawable.cercle,
            arrayOf("Étoile", "Rectangle", "Cercle"), 2, "Un cercle vert"
        ),
        Question(
            "Combien de branches possède cette étoile ?", R.drawable.etoile,
            arrayOf("Quatre", "Cinq", "Six"), 1, "Une étoile orange à cinq branches"
        ),
        Question(
            "Quelle est cette forme à quatre angles droits et côtés inégaux ?", R.drawable.rectangle,
            arrayOf("Rectangle", "Losange", "Triangle"), 0, "Un rectangle violet"
        )
    )

    // --- État du jeu ---
    private var indexCourant = 0          // question affichée
    private var score = 0                 // nombre de bonnes réponses
    private var reponsesDonnees = 0       // progression : nombre de réponses données
    private var aRepondu = false          // la question courante a-t-elle déjà reçu une réponse ?
    private var choixSelectionne = -1     // indice du bouton cliqué
    private var partieTerminee = false    // true quand le résultat final est affiché

    private lateinit var boutonsChoix: List<Button>
    private var teinteParDefaut: ColorStateList? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        boutonsChoix = listOf(binding.btnChoix1, binding.btnChoix2, binding.btnChoix3)
        teinteParDefaut = binding.btnChoix1.backgroundTintList
        binding.progressQuiz.max = questions.size

        // Un seul listener par bouton : l'indice du bouton cliqué = indice du choix.
        boutonsChoix.forEachIndexed { indice, bouton ->
            bouton.setOnClickListener { verifierReponse(indice) }
        }
        binding.btnSuivant.setOnClickListener { passerSuite() }
        binding.btnRejouer.setOnClickListener { rejouer() }

        // Restauration après rotation (amélioration facultative)
        if (savedInstanceState != null) {
            indexCourant = savedInstanceState.getInt(KEY_INDEX)
            score = savedInstanceState.getInt(KEY_SCORE)
            reponsesDonnees = savedInstanceState.getInt(KEY_REPONSES)
            aRepondu = savedInstanceState.getBoolean(KEY_A_REPONDU)
            choixSelectionne = savedInstanceState.getInt(KEY_CHOIX)
            partieTerminee = savedInstanceState.getBoolean(KEY_TERMINEE)
        }

        if (partieTerminee) afficherResultat() else afficherQuestion()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_INDEX, indexCourant)
        outState.putInt(KEY_SCORE, score)
        outState.putInt(KEY_REPONSES, reponsesDonnees)
        outState.putBoolean(KEY_A_REPONDU, aRepondu)
        outState.putInt(KEY_CHOIX, choixSelectionne)
        outState.putBoolean(KEY_TERMINEE, partieTerminee)
    }

    /** Affiche la question courante (et la correction si elle a déjà reçu une réponse). */
    private fun afficherQuestion() {
        val q = questions[indexCourant]

        binding.zoneQuestion.visibility = View.VISIBLE
        binding.zoneResultat.visibility = View.GONE
        binding.txtNumero.text = getString(R.string.numero_format, indexCourant + 1, questions.size)
        binding.imgQuestion.setImageResource(q.imageRes)
        binding.imgQuestion.contentDescription = q.description
        binding.txtQuestion.text = q.texte

        boutonsChoix.forEachIndexed { i, bouton ->
            bouton.text = q.choix[i]
            bouton.backgroundTintList = teinteParDefaut
            bouton.isEnabled = !aRepondu
        }
        binding.txtFeedback.visibility = View.GONE
        binding.btnSuivant.visibility = View.GONE

        if (aRepondu) afficherCorrection()
        actualiserBandeau()
    }

    /** Vérifie le choix cliqué. Un seul point maximum par question. */
    private fun verifierReponse(indiceChoix: Int) {
        if (aRepondu) return              // garde : empêche tout second point
        aRepondu = true
        choixSelectionne = indiceChoix
        reponsesDonnees++                 // la progression avance dans tous les cas

        if (indiceChoix == questions[indexCourant].bonneReponse) score++

        boutonsChoix.forEach { it.isEnabled = false }
        afficherCorrection()
        actualiserBandeau()
    }

    /** Message juste/faux (texte + symbole + couleur) et bouton de navigation. */
    private fun afficherCorrection() {
        val q = questions[indexCourant]
        val juste = choixSelectionne == q.bonneReponse

        binding.txtFeedback.text = if (juste) {
            getString(R.string.bonne_reponse)
        } else {
            getString(R.string.mauvaise_reponse, q.choix[q.bonneReponse])
        }
        binding.txtFeedback.setTextColor(
            ContextCompat.getColor(this, if (juste) R.color.juste else R.color.faux)
        )
        binding.txtFeedback.visibility = View.VISIBLE

        // Marquage visuel des boutons : la bonne réponse est toujours signalée par ✔
        boutonsChoix.forEachIndexed { i, bouton ->
            when {
                i == q.bonneReponse -> {
                    bouton.text = "✔ " + q.choix[i]
                    bouton.backgroundTintList = ColorStateList.valueOf(
                        ContextCompat.getColor(this, R.color.juste)
                    )
                }
                i == choixSelectionne -> {
                    bouton.text = "✘ " + q.choix[i]
                    bouton.backgroundTintList = ColorStateList.valueOf(
                        ContextCompat.getColor(this, R.color.faux)
                    )
                }
            }
        }

        val derniere = indexCourant == questions.size - 1
        binding.btnSuivant.text = getString(if (derniere) R.string.voir_resultat else R.string.question_suivante)
        binding.btnSuivant.visibility = View.VISIBLE
    }

    /** Passe à la question suivante, ou au résultat après la dernière. */
    private fun passerSuite() {
        if (!aRepondu) return             // impossible d'avancer sans avoir répondu
        if (indexCourant < questions.size - 1) {
            indexCourant++
            aRepondu = false
            choixSelectionne = -1
            afficherQuestion()
        } else {
            partieTerminee = true
            afficherResultat()
        }
    }

    /** Affiche le score final ; les choix de réponse sont masqués. */
    private fun afficherResultat() {
        binding.zoneQuestion.visibility = View.GONE
        binding.zoneResultat.visibility = View.VISIBLE
        binding.txtNumero.text = getString(R.string.numero_fin)
        binding.txtResultat.text = getString(R.string.resultat_format, score, questions.size)
        actualiserBandeau()
    }

    /** Remet le quiz à zéro. */
    private fun rejouer() {
        indexCourant = 0
        score = 0
        reponsesDonnees = 0
        aRepondu = false
        choixSelectionne = -1
        partieTerminee = false
        afficherQuestion()
    }

    /** Met à jour le score (haut droite) et la barre de progression. */
    private fun actualiserBandeau() {
        binding.txtScore.text = getString(R.string.score_format, score, questions.size)
        binding.progressQuiz.progress = reponsesDonnees
    }

    companion object {
        private const val KEY_INDEX = "index"
        private const val KEY_SCORE = "score"
        private const val KEY_REPONSES = "reponses"
        private const val KEY_A_REPONDU = "a_repondu"
        private const val KEY_CHOIX = "choix"
        private const val KEY_TERMINEE = "terminee"
    }
}
