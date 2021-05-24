import sbt._

object Dependencies {
  import Version._

  object Cats {
    lazy val core    = "org.typelevel" %% "cats-core"   % catsVersion
    lazy val effects = "org.typelevel" %% "cats-effect" % catsEffectVersion
  }

  object Http4s {
    lazy val http4sDsl           = "org.http4s" %% "http4s-dsl"             % http4sVersion
    lazy val http4sBlazeServer   = "org.http4s" %% "http4s-blaze-server"    % http4sVersion
    lazy val http4sBlazeClient   = "org.http4s" %% "http4s-blaze-client"    % http4sVersion
    lazy val http4sCirce         = "org.http4s" %% "http4s-circe"           % http4sVersion
    lazy val http4sJdkHttpClient = "org.http4s" %% "http4s-jdk-http-client" % "0.3.6"
  }

  object Circe {
    lazy val circeCore          = "io.circe" %% "circe-core"           % circeVersion
    lazy val circeGeneric       = "io.circe" %% "circe-generic"        % circeVersion
    lazy val circeGenericExtras = "io.circe" %% "circe-generic-extras" % circeVersion
    lazy val circeOptics        = "io.circe" %% "circe-optics"         % circeVersion
    lazy val circeParser        = "io.circe" %% "circe-parser"         % circeVersion
  }

  lazy val scalaTest        = "org.scalatest"     %% "scalatest"         % "3.2.8"
  lazy val log4catsSlf4j    = "io.chrisdavenport" %% "log4cats-slf4j"    % log4CatsVersion
  lazy val logbackClassic   = "ch.qos.logback"    % "logback-classic"    % "1.2.3"
  lazy val epimetheusHttp4s = "io.chrisdavenport" %% "epimetheus-http4s" % epimetheusVersion
  lazy val slf4jNop         = "org.slf4j"         % "slf4j-nop"          % "1.6.4"

}

object Version {

  val http4sVersion     = "0.21.7"
  val circeVersion      = "0.13.0"
  val catsVersion       = "2.2.0"
  val catsEffectVersion = "2.2.0"
  val epimetheusVersion = "0.4.2"
  val log4CatsVersion   = "1.1.1"

}
