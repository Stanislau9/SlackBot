package com.github.stanislau9.slackPollBot

import cats.effect.Sync
import org.http4s.dsl.Http4sDsl
import serverSlack.MessageService

case class Routes[F[_]: Sync](messageService: MessageService[F]) extends Http4sDsl[F] {}
