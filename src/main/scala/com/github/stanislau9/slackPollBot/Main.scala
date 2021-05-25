package com.github.stanislau9.slackPollBot

import cats.effect.{ExitCode, IO, IOApp}
import com.github.stanislau9.slackPollBot.Client.MessageClient
import com.github.stanislau9.slackPollBot.MessageHandle.MessageService
import com.github.stanislau9.slackPollBot.Server.MessageServer

object Main extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = {
    val client = MessageClient.res[IO]
    for {
      service <- MessageService.of[IO]
      _       <- MessageServer.startWith[IO](service, client)
    } yield ExitCode.Success
  }
}
