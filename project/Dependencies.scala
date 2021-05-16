import sbt._

object Dependencies {
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.2.8"
}

object Version {
  val http4sVersion         = "0.21.7"
  val circeVersion          = "0.13.0"
  val playVersion           = "2.8.2"
  val doobieVersion         = "0.9.0"
  val catsVersion           = "2.2.0"
  val catsTaglessVersion    = "0.11"
  val catsEffectVersion     = "2.2.0"
  val epimetheusVersion     = "0.4.2"
  val catsScalacheckVersion = "0.2.0"

  val akkaVersion          = "2.6.9"
  val akkaHttpVersion      = "10.1.11"
  val akkaHttpCirceVersion = "1.31.0"

  val log4CatsVersion = "1.1.1"

  val scalaTestVersion = "3.1.0.0-RC2"
  val h2Version        = "1.4.200"
  val slickVersion     = "3.3.3"
}
