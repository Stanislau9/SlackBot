package server

import cats.effect.IO

import org.http4s.HttpRoutes
import org.http4s.dsl.io._

object routes {

  val routes: HttpRoutes[IO] = HttpRoutes.of[IO] {

    case GET -> Root / value => Ok(value)

    case value @ POST -> Root =>
      println(s"DEBUG ->>>>> ${value.as[String]}")
      Ok(value.as[String])
  }
}
