package serverSlack

import cats.effect.Sync

import org.http4s.UrlForm

import io.circe.{Json, ParsingFailure, parser}
import io.circe.syntax._

import serverSlack.Codecs._

trait MessageService[F[_]] {
  def reply(form: UrlForm): F[Unit]
}

object MessageService {

  def apply[F[_]: Sync]: MessageService[F] = (form: UrlForm) => {

    val payload: Either[ParsingFailure, Json] = form.get("payload").map(parser.parse).headOption.get

    val payloadType: PayloadType = payload.flatMap(_.as[PayloadType]).getOrElse(PayloadType(""))

    payloadType.`type` match {
      case "shortcut" => {
        val triggerId: TriggerId = payload.flatMap(_.as[TriggerId]).getOrElse(TriggerId(""))
        Sync[F].delay(Client.openModal(view.Open(triggerId.triggerId, Templates.modalAddPoll).asJson))
      }
      case "block_actions"   => Sync[F].delay() //----------
      case "view_submission" => Sync[F].delay(Client.postMessage(Message("chat-bot", "modal submitted").asJson))
      case _                 => Sync[F].delay() //----------
    }

  }

}
