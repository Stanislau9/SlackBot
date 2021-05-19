package serverSlack

import cats.effect._
import org.http4s.UrlForm
import org.http4s.HttpRoutes
import org.http4s.dsl.io._
import org.http4s.UrlForm._
import io.circe.parser

import java.net.URLDecoder

import codecs._
import serverSlack.templates._

object routes {

  def routes(implicit cs: ContextShift[IO]): HttpRoutes[IO] = HttpRoutes.of[IO] {

    case value @ POST -> Root =>
      for {
        urlForm <- value.as[UrlForm]

        payload = urlForm
          .get("payload")
          .map(parser.parse)
          .headOption
          .get

        payloadType = payload.flatMap(_.as[PayloadType]).getOrElse(PayloadType(""))

        client <- if (payloadType.payloadType == "shortcut") SlackClient.SendMessage("chat-bot", block)
        else {
          val cont = payload
            .flatMap(_.as[Container])
            .getOrElse(Container(ContainerValue("", "", "")))

          SlackClient.UpdateMessage(cont.container.channelId, blockEdited, cont.container.messageTs)
        }

//        v <- value.as[String]
//        _ <- IO(println(s"DEBUG_to_server ->>>>> ${URLDecoder.decode(v)}"))

        response <- Ok()
      } yield response

  }
}
