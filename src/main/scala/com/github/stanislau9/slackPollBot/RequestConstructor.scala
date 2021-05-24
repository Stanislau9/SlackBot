package com.github.stanislau9.slackPollBot
import com.github.stanislau9.slackPollBot.Resources.Config
import com.github.stanislau9.slackPollBot.Resources.Models.MessageModels.{Chat, Response, View}
import io.circe.Json
import org.http4s.{Header, Method, Request, Uri}
import io.circe.syntax._
import org.http4s.circe.jsonEncoder

object RequestConstructor {

  def create[F[_]](response: Option[Response]): Request[F] = {

    val authHeader: Header = Header("Authorization", Config.token)

    def construct(uri: Uri, body: Json): Request[F] =
      Request[F](Method.POST, uri).withHeaders(authHeader).withEntity(body)

    response match {
      case Some(value) =>
        value match {
          case resp: View.Open => {
            println(resp.asJson)
            construct(Config.viewOpen, resp.asJson)
          }
          case resp: View.Update => construct(Config.viewUpdate, resp.asJson)
          case resp: Chat.Post   => construct(Config.chatPostMessage, resp.asJson)
          case resp: Chat.Update => construct(Config.chatUpdateMessage, resp.asJson)
        }
      case None => construct(Config.viewOpen, None.asJson)
    }

  }
}
