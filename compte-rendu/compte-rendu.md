# Compte rendu – Quiz en images

**Étudiant(s) :** _à compléter_  
**Thème choisi :** formes géométriques (triangle, carré, cercle, étoile, rectangle)

## 1. Interface et widgets
- `TextView` : titre, score (`txt_score`, en haut à droite), numéro de question, énoncé, message de correction, résultat final
- `ProgressBar` horizontale (`progress_quiz`, max = 5)
- `ImageView` (`img_question`, `scaleType=fitCenter`, sans déformation)
- Trois `Button` (`btn_choix1` à `btn_choix3`), `btn_suivant`, `btn_rejouer`
- `ScrollView` pour les petits écrans ; le bandeau titre/score/progression reste fixe en haut
- Zone résultat final (`zone_resultat`) dans le même layout

## 2. Tableau des questions
`data class Question(texte, imageRes, choix: Array<String>, bonneReponse: Int, description)`.
Le tableau `questions` contient 5 éléments. Chaque image est liée par `R.drawable.xxx`
(vecteurs XML dans `res/drawable`). La bonne réponse est un indice (0, 1 ou 2) : 1, 0, 2, 1, 0.

## 3. Événements, score et progression
- Chaque bouton a son listener ; l'indice du bouton cliqué (0, 1, 2) est passé à `verifierReponse(indice)`
  et comparé à `bonneReponse`.
- Le **score** augmente de 1 uniquement si le choix est juste.
- La **progression** (`reponsesDonnees`) augmente à chaque réponse, juste ou fausse.
- Le booléen `aRepondu` + la désactivation des boutons empêchent plusieurs points par question.
- La dernière question est détectée avec `indexCourant == questions.size - 1` : le bouton devient
  « Voir le résultat » et affiche la zone finale ; aucun dépassement du tableau n'est possible.
- Rotation : l'état est conservé avec `onSaveInstanceState`.

## 4. Tests réalisés
| Test | Résultat |
|---|---|
| Nouvelle partie | 0/5, progression 0/5 – _à valider_ |
| Bonne réponse | score +1, progression +1 – _à valider_ |
| Mauvaise réponse | score inchangé, progression +1 – _à valider_ |
| Clics multiples | aucun point en plus – _à valider_ |
| 5 justes / 5 fausses | 5/5 / 0/5 – _à valider_ |
| Rejouer | remise à zéro – _à valider_ |
| Petit écran | défilement OK – _à valider_ |

## 5. Difficultés rencontrées
_à compléter_
