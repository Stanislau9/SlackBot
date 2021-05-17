package serverSlack

import io.circe.Json
import io.circe.generic.JsonCodec

object slackApiMethods {
  object chat {

    @JsonCodec case class triggerId(trigger_id: String)

    @JsonCodec case class postMessage(channel: String,
                                      text: String = "",
                                      blocks: Array[Json] = Array(),
                                      attachments: Array[Json] = Array(),
                                      username: String = "")
  }
}
