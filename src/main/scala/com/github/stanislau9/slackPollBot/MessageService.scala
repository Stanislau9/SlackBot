package com.github.stanislau9.slackPollBot

import com.github.stanislau9.slackPollBot.Resources.Models.PayloadModels._
import com.github.stanislau9.slackPollBot.MessageConstructor._
import com.github.stanislau9.slackPollBot.Resources.Models.MessageModels.Response
import cats.Monad
import org.http4s.UrlForm
import io.circe.parser.parse
import cats.data.OptionT

trait MessageService[F[_]] {

  def handle(form: UrlForm): F[Option[Response]]
}

object MessageService {

  def of[F[_]: Monad]: MessageService[F] = new MessageService[F] {
    override def handle(form: UrlForm): F[Option[Response]] = {

      val result: Option[Response] = for {
        payloadStr  <- form.getFirst("payload")
        payloadJson <- parse(payloadStr).toOption
        payload     <- payloadJson.as[Payload].toOption
        message <- payload match {
          case Shortcut(triggerId) => openView(triggerId, payloadJson)
          case BlockActions(user, container) =>
            container match {
              case View(viewId)                  => updateView(viewId, payloadJson)
              case Message(messageTs, channelId) => updateMessage(messageTs, channelId, payloadJson)
            }
          case ViewSubmission() => sendMessage(payloadJson)
        }
      } yield message

      OptionT.fromOption(result).value

    }
  }

}
