package server

import cats.effect.IO

import org.http4s.HttpRoutes
import org.http4s.dsl.io._

import org.http4s.circe._
import io.circe.syntax._

object routes {

  val routes: HttpRoutes[IO] = HttpRoutes.of[IO] {

    case GET -> Root / value => Ok(value)

    case value @ POST -> Root =>
      for {
        v <- value.as[String]
        _ <- IO(println(s"DEBUG ->>>>> $v"))
        response <- Ok(
          "{\n    \"text\": \"I am a test message\",\n    \"attachments\": [\n        {\n            \"text\": \"And here’s an attachment!\"\n        }\n    ]\n})".asJson)
      } yield response

  }
}
