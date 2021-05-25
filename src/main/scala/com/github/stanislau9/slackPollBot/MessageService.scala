package com.github.stanislau9.slackPollBot

import com.github.stanislau9.slackPollBot.Resources.Models.PayloadModels._
import com.github.stanislau9.slackPollBot.MessageConstructor._
//import com.github.stanislau9.slackPollBot.Resources.Models.MessageModels.Response
import com.github.stanislau9.slackPollBot.Resources.Models.MessageModels2.Response

import org.http4s.UrlForm

import io.circe.parser.parse
import cats.data.State
import cats.effect.Sync
import cats.effect.concurrent.Ref

import cats.syntax.all._

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
              val a = for {
                payloadStr  <- form.getFirst("payload")
                payloadJson <- parse(payloadStr).toOption
                payload     <- payloadJson.as[Payload].toOption
                message <- payload match {
                  case Shortcut(triggerId) => openView(triggerId)
                  case BlockActions(user, container) =>
                    container match {
                      case View(viewId) => updateView(viewId, payloadJson)
                      case Message(messageTs, channelId) => {
                        value + (messageTs -> Set("ddd"))
                        sendMessage(payloadJson)
                      } ///updateMessage(messageTs, channelId, payloadJson)
                    }
                  case ViewSubmission() => sendMessage(payloadJson)
                }
              } yield message
              println(s">>>>>>>>>>>>>>> $value")
              (value, a)
            }

          }
//          Sync[F].delay(
//            for {
//              payloadStr  <- form.getFirst("payload")
//              payloadJson <- parse(payloadStr).toOption
//              payload     <- payloadJson.as[Payload].toOption
//              message <- payload match {
//                case Shortcut(triggerId) => openView(triggerId)
//                case BlockActions(user, container) =>
//                  container match {
//                    case View(viewId)                  => updateView(viewId, payloadJson)
//                    case Message(messageTs, channelId) => ??? ///updateMessage(messageTs, channelId, payloadJson)
//                  }
//                case ViewSubmission() => sendMessage(payloadJson)
//              }
//            } yield message
//          )
      }
    }

  }

}
