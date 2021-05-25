package com.github.stanislau9.slackPollBot.Server

import cats.effect.{ConcurrentEffect, Resource, Sync, Timer}
import com.github.stanislau9.slackPollBot.MessageHandle.MessageService
import com.github.stanislau9.slackPollBot.Resources.Config
import org.http4s.client.Client
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.syntax.all._

import scala.concurrent.ExecutionContext

object MessageServer {

  def startWith[F[_]: Sync](messageService: MessageService[F], client: Resource[F, Client[F]])(
      implicit concurrent: ConcurrentEffect[F],
      timer: Timer[F]): F[Unit] =
    BlazeServerBuilder[F](ExecutionContext.global)
      .bindHttp(port = Config.port, host = Config.host)
      .withHttpApp(Routes(messageService, client).routes.orNotFound)
      .serve
      .compile
      .drain

}
