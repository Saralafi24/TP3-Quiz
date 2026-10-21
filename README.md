# Quiz en images (Android – Kotlin / XML)

Quiz de 5 questions illustrées (thème : formes géométriques), 3 boutons de choix par question,
score, barre de progression, écran final et bouton **Rejouer**.

## Ouvrir et lancer
1. Installer **Android Studio** (Koala ou plus récent) avec le SDK Android 34.
2. `File > Open` puis sélectionner ce dossier `QuizImages`.
3. Attendre la synchronisation Gradle (connexion Internet requise la 1re fois).
4. Choisir un émulateur ou un téléphone, puis cliquer sur **Run ▶**.

## Contenu du dépôt
- `app/src/main/java/com/example/quizimages/` : `MainActivity.kt`, `Question.kt`
- `app/src/main/res/layout/activity_main.xml` : interface XML
- `app/src/main/res/drawable/` : les 5 images (triangle, carre, cercle, etoile, rectangle)
- `captures/` : captures d'écran (à ajouter après exécution)
- `compte-rendu/compte-rendu.md` : compte rendu du TP

## Gradle Wrapper
`gradlew`, `gradlew.bat` et `gradle-wrapper.properties` (Gradle 8.7) sont fournis.
Si `gradle/wrapper/gradle-wrapper.jar` manque, ouvrir le projet dans Android Studio
ou exécuter `gradle wrapper --gradle-version 8.7`.
