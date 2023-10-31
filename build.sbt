import sbt.Keys.libraryDependencies

name := "scr-2023-09-play"
organization := "ru.otus"

version := "0.1"

scalaVersion := "2.11.5"


val root = (project in file("."))
  .settings(
    libraryDependencies += "net.codingwell" %% "scala-guice" % "4.0.0",
    libraryDependencies += "org.liquibase" % "liquibase-core" % "3.4.2",
    libraryDependencies += "org.squeryl" %% "squeryl" % "0.9.5-7",
    libraryDependencies += "com.zaxxer" % "HikariCP" % "4.0.1",
    libraryDependencies += "org.postgresql" % "postgresql" % "42.3.1",
    libraryDependencies ++= Seq(
          "com.typesafe.slick" %% "slick" % "3.3.3",
          "org.slf4j" % "slf4j-nop" % "1.6.4",
          "com.typesafe.slick" %% "slick-hikaricp" % "3.3.3"
      )
  )
  .enablePlugins(PlayScala)


