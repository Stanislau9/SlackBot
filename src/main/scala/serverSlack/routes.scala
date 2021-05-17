package serverSlack

import cats.effect.IO
import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder
import io.circe.syntax.EncoderOps
import org.http4s.{EntityDecoder, HttpRoutes}
import org.http4s.circe.CirceEntityCodec.circeEntityDecoder
import org.http4s.circe.jsonOf
import org.http4s.dsl.io._
import serverSlack.slackApiMethods.chat._

object routes {

  implicit val authorDecoder: Decoder[postMessage]                 = deriveDecoder
  implicit val authorDecoderEntity: EntityDecoder[IO, postMessage] = jsonOf

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
