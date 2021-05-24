package serverSlack
import cats.syntax.all._
import cats.effect.Sync
import io.circe
import io.circe.Decoder.Result
import org.http4s.UrlForm
import serverSlack.PayloadType._
import serverSlack.View._
import io.circe.{DecodingFailure, Json, ParsingFailure, parser}
import io.circe.syntax._

trait MessageService[F[_]] {
  def reply(form: UrlForm): F[Unit]
}

object MessageService {

  def apply[F[_]: Sync]: MessageService[F] = (form: UrlForm) => {

    val res: Either[circe.Error, F[Unit]] = for {

      payloadJson <- parser.parse(form.getFirstOrElse("payload", ""))
      payload     <- payloadJson.as[Payload]
      s = payload match {
        case Shortcut(triggerId) =>
          Sync[F].delay(println(Client.openModal(Open(triggerId, MessageBuilder.viewConstructor()).asJson)))
        case BlockActions(user, container) =>
          container match {
            case View(viewId) =>
              Sync[F].delay(
                println(Client.updateModal(Update(viewId, MessageBuilder.viewConstructor(payloadJson)).asJson)))
            case Message(messageTs, channelId) =>
              Sync[F].delay(println(
                Client.updateMessage(Chat.Update(channelId, messageTs, MessageBuilder.updatePoll(payloadJson)).asJson)))
          }
        case ViewSubmission() =>
          Sync[F]
            .delay(
              println(Client.postMessage(Chat.Post("chat-bot", MessageBuilder.PollConstructor(payloadJson)).asJson)))
      }

    } yield s

    res.sequence.map(_ => ())

  }
}
