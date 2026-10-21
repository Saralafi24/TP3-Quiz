#!/bin/sh
# Lanceur Gradle Wrapper (version minimale).
# Si gradle-wrapper.jar est absent, regenerez-le avec : gradle wrapper --gradle-version 8.7
# (ou ouvrez simplement le projet dans Android Studio, qui gere Gradle automatiquement).
APP_HOME=$(cd "$(dirname "$0")" && pwd -P)
JAR="$APP_HOME/gradle/wrapper/gradle-wrapper.jar"
if [ -f "$JAR" ]; then
  exec java -classpath "$JAR" org.gradle.wrapper.GradleWrapperMain "$@"
elif command -v gradle >/dev/null 2>&1; then
  exec gradle "$@"
else
  echo "gradle-wrapper.jar introuvable et 'gradle' non installe. Ouvrez le projet avec Android Studio." >&2
  exit 1
fi
