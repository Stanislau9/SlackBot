package serverSlack

import scalaj.http._
import io.circe.Json

object Client {

  private def send(url: String, requestBody: Json): HttpResponse[String] =
    Http(url)
      .method("POST")
      .header("Authorization", Config.token)
      .header("content-type", "application/json")
      .postData(requestBody.toString)
      .asString

  def postMessage(request: Json): HttpResponse[String] = send(Config.chatPostMessage, request)

  def updateMessage(request: Json): HttpResponse[String] = send(Config.chatUpdateMessage, request)

  def openModal(request: Json): HttpResponse[String] = send(Config.viewOpen, request)

  def updateModal(request: Json): HttpResponse[String] = send(Config.viewUpdate, request)

}
