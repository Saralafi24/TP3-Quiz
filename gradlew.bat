@echo off
set APP_HOME=%~dp0
set JAR=%APP_HOME%gradle\wrapper\gradle-wrapper.jar
if exist "%JAR%" (
  java -classpath "%JAR%" org.gradle.wrapper.GradleWrapperMain %*
) else (
  gradle %*
)
