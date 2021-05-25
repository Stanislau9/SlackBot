package com.github.stanislau9.slackPollBot.Server

import cats.effect.{Resource, Sync}
import cats.syntax.all._
import com.github.stanislau9.slackPollBot.MessageHandle.{MessageService, RequestConstructor}
import com.github.stanislau9.slackPollBot.MessageHandle.RequestConstructor
import org.http4s.client.Client
import org.http4s.dsl.Http4sDsl
import org.http4s.{HttpRoutes, UrlForm}

case class Routes[F[_]: Sync](messageService: MessageService[F], client: Resource[F, Client[F]]) extends Http4sDsl[F] {

  val routes: HttpRoutes[F] = HttpRoutes.of[F] {

    case request @ POST -> Root =>
      for {
        urlForm    <- request.as[UrlForm]
        optionResp <- messageService.handle(urlForm)
        req = RequestConstructor.create[F](optionResp)
        _ <- client.use { client =>
          for {
            _ <- client.expect[String](req)
          } yield ()
        }
        response <- Ok()
      } yield response

  }

}
