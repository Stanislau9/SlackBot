import Dependencies._

ThisBuild / scalaVersion := "2.13.5"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "SlackBot",
    libraryDependencies ++= Seq(
      scalaTest % Test,
      cats_core,
      cats_effect,
      http4s_dsl,
      http4s_blaze_server,
      http4s_blaze_client,
      http4s_circe,
      circe_core,
      circe_generic,
      circe_generic_extras,
      circe_optics,
      circe_parser,
      http4s_jdk_http_client
    )
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
