import sbt._



object Dependencies {
  import Version._
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.2.8"
  lazy val cats_core = "org.typelevel" %% "cats-core" % catsVersion
  lazy val cats_effect = "org.typelevel" %% "cats-effect" % catsEffectVersion
  lazy val http4s_dsl = "org.http4s" %% "http4s-dsl" % http4sVersion
  lazy val http4s_blaze_server = "org.http4s" %% "http4s-blaze-server" % http4sVersion
  lazy val http4s_blaze_client = "org.http4s" %% "http4s-blaze-client" % http4sVersion
  lazy val http4s_circe = "org.http4s" %% "http4s-circe" % http4sVersion
  lazy val circe_core = "io.circe" %% "circe-core" % circeVersion
  lazy val circe_generic = "io.circe" %% "circe-generic" % circeVersion
  lazy val circe_generic_extras = "io.circe" %% "circe-generic-extras" % circeVersion
  lazy val circe_optics = "io.circe" %% "circe-optics" % circeVersion
  lazy val circe_parser = "io.circe" %% "circe-parser" % circeVersion
  lazy val http4s_jdk_http_client= "org.http4s" %% "http4s-jdk-http-client" % "0.3.6"
}


object Version {
  val catsVersion = "2.2.0"
  val catsEffectVersion = "2.2.0"
  val http4sVersion = "0.21.7"
  val circeVersion = "0.13.0"
}
