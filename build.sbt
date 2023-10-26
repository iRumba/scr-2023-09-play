name := "scr-2023-09-play"
organization := "ru.otus"

version := "0.1"

scalaVersion := "2.11.5"


val root = (project in file("."))
  .settings(
    libraryDependencies += "net.codingwell" %% "scala-guice" % "4.0.0"
  )
  .enablePlugins(PlayScala)


