package serverSlack

import cats.effect._
import cats.syntax.all._

import org.http4s.UrlForm
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl

import java.net.URLDecoder

case class Routes[F[_]: Sync](messageService: MessageService[F]) extends Http4sDsl[F] {

  val routes: HttpRoutes[F] = HttpRoutes.of[F] {

    case request @ POST -> Root =>
      for {
        urlForm <- request.as[UrlForm]
        _       <- messageService.reply(urlForm)

        str <- request.as[String]
        _   <- Sync[F].delay(println(s"DEBUG_to_server ->>>>> ${URLDecoder.decode(str)}"))

        response <- Ok()
      } yield response

  }
}
