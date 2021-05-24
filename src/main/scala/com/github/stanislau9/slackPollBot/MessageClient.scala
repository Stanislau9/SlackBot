package com.github.stanislau9.slackPollBot

import cats.effect.{ConcurrentEffect, Resource}
import org.http4s.client.Client
import org.http4s.client.blaze.BlazeClientBuilder

import scala.concurrent.ExecutionContext

object MessageClient {

  def res[F[_]: ConcurrentEffect]: Resource[F, Client[F]] =
    BlazeClientBuilder[F](ExecutionContext.global).resource

}
