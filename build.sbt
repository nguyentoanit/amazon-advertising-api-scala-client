import Dependencies._

ThisBuild / scalaVersion     := "2.12.8"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.AmazonAdvertisingApi"
ThisBuild / organizationName := "AmazonExample"

lazy val root = (project in file("."))
  .settings(
    name := "Amazon Advertising API Scala SDK",
    libraryDependencies ++= Seq(
      "org.specs2" %% "specs2-core" % "4.3.4" % "test",
      "org.scalaj" %% "scalaj-http" % "2.4.1",
      "com.typesafe.play" %% "play-json" % "2.7.2"
    )
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
