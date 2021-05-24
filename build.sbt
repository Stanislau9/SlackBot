import Dependencies._

ThisBuild / scalaVersion := "2.13.5"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "SlackBot",
    scalacOptions ++= Seq(
      "-deprecation",
      "-feature",
      "-Ymacro-annotations",
    ),
    libraryDependencies ++= Seq(
      scalaTest % Test,
      Cats.core,
      Cats.effects,
      Http4s.http4sDsl,
      Http4s.http4sCirce,
      Http4s.http4sJdkHttpClient,
      Http4s.http4sBlazeClient,
      Http4s.http4sBlazeServer,
      Circe.circeCore,
      Circe.circeOptics,
      Circe.circeParser,
      Circe.circeGeneric,
      Circe.circeGenericExtras,
      log4catsSlf4j,
      logbackClassic,
      epimetheusHttp4s,
      slf4jNop,
    )
  )
