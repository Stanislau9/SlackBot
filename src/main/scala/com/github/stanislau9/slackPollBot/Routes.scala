package com.github.stanislau9.slackPollBot

import cats.syntax.all._
import cats.effect.{Resource, Sync}
import org.http4s.{HttpRoutes, UrlForm}
import org.http4s.dsl.Http4sDsl
import org.http4s.client.Client
import java.net.URLDecoder //delete

case class Routes[F[_]: Sync](messageService: MessageService[F], client: Resource[F, Client[F]]) extends Http4sDsl[F] {

  val routes: HttpRoutes[F] = HttpRoutes.of[F] {

    case request @ POST -> Root =>
      for {

        str <- request.as[String]                                                          //delete
        _   <- Sync[F].delay(println(s"DEBUG_to_server ->>>>> ${URLDecoder.decode(str)}")) //delete

        urlForm    <- request.as[UrlForm]
        optionResp <- messageService.handle(urlForm)
        req = RequestConstructor.create[F](optionResp)
        _ <- client.use { client =>
          for {
            str <- client.expect[String](req)
            _   <- Sync[F].delay(println(s"DEB_to_client->>>>>>>>>>>>>>>$str")) //delete
          } yield ()
        }
        response <- Ok()
      } yield response

  }

}
