import Dependencies._
import Version._

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
      "org.typelevel" %% "cats-core" % catsVersion,
      "org.typelevel" %% "cats-effect" % catsEffectVersion,
      "org.http4s" %% "http4s-dsl" % http4sVersion,
      "org.http4s" %% "http4s-blaze-server" % http4sVersion,
      "org.http4s" %% "http4s-blaze-client" % http4sVersion,
      "org.http4s" %% "http4s-circe" % http4sVersion,
      "org.http4s" %% "http4s-jdk-http-client" % "0.3.6",
      "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
      "de.heikoseeberger" %% "akka-http-circe" % akkaHttpCirceVersion,
      "com.typesafe.akka" %% "akka-stream" % akkaVersion,
      "io.chrisdavenport" %% "log4cats-slf4j" % log4CatsVersion,
      "ch.qos.logback" % "logback-classic" % "1.2.3",
      "com.codecommit" %% "cats-effect-testing-scalatest" % "0.4.1" % Test,
      "io.chrisdavenport" %% "epimetheus-http4s" % epimetheusVersion,
      "io.chrisdavenport" %% "cats-scalacheck" % catsScalacheckVersion % Test,
      "org.scalatestplus" %% "scalatestplus-scalacheck" % scalaTestVersion % Test,
      "org.scalatestplus" %% "selenium-2-45" % scalaTestVersion % Test,
      "org.typelevel" %% "simulacrum" % "1.0.0",
      "org.tpolecat" %% "atto-core" % "0.8.0",
      "io.circe" %% "circe-core" % circeVersion,
      "io.circe" %% "circe-generic" % circeVersion,
      "io.circe" %% "circe-generic-extras" % circeVersion,
      "io.circe" %% "circe-optics" % circeVersion,
      "io.circe" %% "circe-parser" % circeVersion,
      "com.typesafe.akka" %% "akka-actor" % akkaVersion,
      "com.typesafe.akka" %% "akka-persistence" % akkaVersion,
      "com.typesafe.akka" %% "akka-cluster" % akkaVersion,
      "com.typesafe.akka" %% "akka-cluster-sharding" % akkaVersion,
      "org.fusesource.leveldbjni" % "leveldbjni-all" % "1.8",
      "org.tpolecat" %% "doobie-core" % doobieVersion,
      "org.tpolecat" %% "doobie-h2" % doobieVersion,
      "org.tpolecat" %% "doobie-hikari" % doobieVersion,
      "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test,
      "org.mockito" %% "mockito-scala" % "1.15.0" % Test,
      "org.scalaj" %% "scalaj-http" % "2.4.2" % Test,
      "org.tpolecat" %% "doobie-scalatest" % doobieVersion % Test,
      "org.typelevel" %% "cats-tagless-macros" % catsTaglessVersion,
      "com.h2database" % "h2" % "1.4.200",
      "eu.timepit" %% "refined" % "0.9.17",
      "com.typesafe.slick" %% "slick" % slickVersion,
      "org.slf4j" % "slf4j-nop" % "1.6.4",
      "com.typesafe.slick" %% "slick-hikaricp" % slickVersion,
    )
  )



// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
