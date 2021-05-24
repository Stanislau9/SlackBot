package com.github.stanislau9.slackPollBot

import com.github.stanislau9.slackPollBot.Resources.Models.PayloadModels._
import org.http4s.UrlForm
import io.circe.parser._
import cats.Monad
import cats.data.EitherT

import io.circe.Decoder.Result
import io.circe.{DecodingFailure, Json, ParsingFailure}

trait MessageService[F[_]] {

  def handle(form: UrlForm): F[Either[String, Message]]
}

object MessageService {

  def of[F[_]]: MessageService[F] = new MessageService[F] {
    override def handle(form: UrlForm): F[Either[String, Message]] = {
      val payloadStr: String = form.getFirstOrElse("payload", "")

      val payloadJson = EitherT.fromEither(parse(payloadStr))
      val payload =
        EitherT.fromEither(parse(payloadStr).map(_.as[Payload]))
//      val tempEither = for {

//        payloadJson <- parse(payloadStr)
//        payload     <- payloadJson.as[Payload]
//        message = payload match {
      ////          case Shortcut(triggerId) => OpenView()
      ////          case BlockActions(user, container) =>
      ////            container match {
      ////              case View(viewId)                  => UpdateView()
      ////              case Message(messageTs, channelId) => UpdateMessage()
      ////            }
      ////          case ViewSubmission() => SendMessage()
      ////        }
//      } yield ()

      //val a = EitherT.fromEither(tempEither)
      ???

    }
  }

}
