package serverSlack

import cats.effect.{ContextShift, IO}

import io.circe.syntax._
import io.circe.Json

import org.http4s.syntax.all._
import org.http4s.{Header, Method, Request, Uri}
import org.http4s.client.Client
import org.http4s.client.blaze.BlazeClientBuilder
import org.http4s.circe.jsonEncoder

import scala.concurrent.ExecutionContext

import codecs._

object SlackClient {

  private val authHeader: Header =
    Header("Authorization", "Bearer xoxp-2032270042418-2025493000822-2060772335680-4b96e3374b57433b98f6316d16344d6f")

  private def Send(request: Request[IO])(implicit cs: ContextShift[IO]): IO[Unit] =
    BlazeClientBuilder[IO](ExecutionContext.global).resource.use { client: Client[IO] =>
      for {
        str <- client.expect[String](request)
        _   <- IO(println(s"DEB_to_client->>>>>>>>>>>>>>>$str"))
      } yield ()
    }

  def SendMessage(channel: String, value: Array[Json])(implicit cs: ContextShift[IO]): IO[Unit] = {
    val postMessage: Uri = uri"https://slack.com/api/chat.postMessage"
    val request: Request[IO] = Request[IO](Method.POST, postMessage)
      .withHeaders(authHeader)
      .withEntity(chat.PostMessage(channel, blocks = value).asJson)
    Send(request)
  }

  def UpdateMessage(channel: String, value: Array[Json], ts: String)(implicit cs: ContextShift[IO]): IO[Unit] = {
    val updateMessage: Uri = uri"https://slack.com/api/chat.update"
    val request: Request[IO] = Request[IO](Method.POST, updateMessage)
      .withHeaders(authHeader)
      .withEntity(chat.UpdateMessage(channel, ts, blocks = value).asJson)
    Send(request)
  }

  def OpenModal(triggerId: String, value: Json)(implicit cs: ContextShift[IO]): IO[Unit] = {
    val openModal: Uri = uri"https://slack.com/api/views.open"
    val request: Request[IO] = Request[IO](Method.POST, openModal)
      .withHeaders(authHeader)
      .withEntity(view.Open(triggerId, value).asJson)
    Send(request)
  }

}
