package com.github.stanislau9.slackPollBot.MessageHandle

import com.github.stanislau9.slackPollBot.MessageHandle.MessageConstructor._
import com.github.stanislau9.slackPollBot.Resources.Models.MessageModels.Update.PayloadMessage
import com.github.stanislau9.slackPollBot.Resources.Models.PayloadModels._
//import com.github.stanislau9.slackPollBot.Resources.Models.MessageModels.Response
import cats.data.State
import cats.effect.Sync
import cats.effect.concurrent.Ref
import cats.syntax.all._
import com.github.stanislau9.slackPollBot.Resources.Models.MessageModels.Response
import io.circe.parser.parse
import org.http4s.UrlForm

trait MessageService[F[_]] {

  def handle(form: UrlForm): F[Option[Response]]
}

object MessageService {

  def of[F[_]: Sync]: F[MessageService[F]] = {
    Ref.of(Map.empty[String, Set[String]]).map { state: Ref[F, Map[String, Set[String]]] =>
      new MessageService[F] {
        override def handle(form: UrlForm): F[Option[Response]] =
          state.modifyState {
            State { value: Map[String, Set[String]] =>
              val res = for {
                payloadStr  <- form.getFirst("payload")
                payloadJson <- parse(payloadStr).toOption
                payload     <- payloadJson.as[Payload].toOption
                message <- payload match {
                  case Shortcut(triggerId) => Some(value, openView(triggerId))
                  case BlockActions(user, container) =>
                    container match {
                      case View(viewId) => Some(value, updateView(viewId, payloadJson))
                      case Message(messageTs, channelId) =>
                        Some {
                          val id: String =
                            payloadJson
                              .as[PayloadMessage]
                              .map(value => value.actions.map(value => value.actionId).head)
                              .getOrElse("0")
                          val keyExist = value.get(messageTs)
                          if (keyExist.isEmpty) {
                            val upd = value.updated(messageTs, Set(s"$user.id$id"))

                            (upd, updateMessage(messageTs, channelId, payloadJson))
                          } else {
                            val usr = value.getOrElse(messageTs, Set())(s"$user.id$id")
                            if (usr) {
                              val upd = value.updated(messageTs, value(messageTs) - s"$user.id$id")

                              (upd, updateMinusMessage(messageTs, channelId, payloadJson))
                            } else {
                              val upd = value.updated(messageTs, value(messageTs) + s"$user.id$id")

                              (upd, updateMessage(messageTs, channelId, payloadJson))
                            }
                          }

                        }
                    }
                  case ViewSubmission() => Some(value, sendMessage(payloadJson))
                }
              } yield message

              res.get
            }

          }

      }
    }

  }

}
