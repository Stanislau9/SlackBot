package serverSlack

import io.circe.Json
import io.circe.generic.JsonCodec
import io.circe.generic.extras.{Configuration, ConfiguredJsonCodec, JsonKey}

object Codecs {

  implicit val config: Configuration = Configuration.default.withSnakeCaseMemberNames

  @ConfiguredJsonCodec final case class PayloadType(`type`: String)

  @ConfiguredJsonCodec final case class TriggerId(triggerId: String)

  @ConfiguredJsonCodec final case class CallBackId(callbackId: String)

  @ConfiguredJsonCodec final case class Message(channel: String, text: String = "", blocks: Array[Json] = Array())

  //---------------------------------
  @ConfiguredJsonCodec case class Container(container: ContainerValue)
  @ConfiguredJsonCodec case class ContainerValue(@JsonKey("type") t: String, messageTs: String, channelId: String)
  object chat {

    @JsonCodec case class PostMessage(channel: String, text: String = "", blocks: Array[Json] = Array())

    @JsonCodec case class UpdateMessage(channel: String, ts: String, text: String = "", blocks: Array[Json] = Array())
  }

  object view {
    @JsonCodec case class Open(trigger_id: String, view: Json)
  }
}
