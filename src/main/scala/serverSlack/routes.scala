package serverSlack

import cats.effect.IO

import org.http4s.HttpRoutes
import org.http4s.circe.CirceEntityCodec.circeEntityDecoder

import org.http4s.dsl.io._
import serverSlack.slackApiMethods.chat._
import io.circe.syntax._

object routes {

  val routes: HttpRoutes[IO] = HttpRoutes.of[IO] {

    case GET -> Root / value => Ok(value)

    case value @ POST -> Root =>
      for {
        v        <- value.as[String]
        _        <- IO(println(s"DEBUG ->>>>> ${v.drop(8).asJson.as[triggerId]}"))
        response <- Ok(value.as[String])
      } yield response

  }
}
