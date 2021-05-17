package server

import cats.effect.IO
import io.circe.Json
import io.circe.generic.JsonCodec
import io.circe.syntax.EncoderOps
import org.http4s.HttpRoutes
import org.http4s.circe.jsonDecoder
import org.http4s.dsl.io._

object routes {
  @JsonCodec case class triggerId(trigger_id: String)
  val routes: HttpRoutes[IO] = HttpRoutes.of[IO] {

    case GET -> Root / value => Ok(value)

    case value @ POST -> Root =>
      for {
        v        <- value.as[String].map(_.drop(8).asJson)
        _        <- IO(println(s"DEBUG ->>>>> ${v.as[triggerId]}"))
        response <- Ok(value.as[String])
      } yield response

  }
}
