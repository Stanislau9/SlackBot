package server

import cats.effect.{ExitCode, IO, IOApp}

import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.syntax.all._

import routes.routes

import scala.concurrent.ExecutionContext

object server extends IOApp {
  override def run(args: List[String]): IO[ExitCode] =
    BlazeServerBuilder[IO](ExecutionContext.global)
      .bindHttp(port = 8080, host = "68.183.217.119")
      .withHttpApp(routes.orNotFound)
      .serve
      .compile
      .drain
      .as(ExitCode.Success)
}
