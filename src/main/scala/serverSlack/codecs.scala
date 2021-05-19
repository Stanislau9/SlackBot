package serverSlack

import io.circe.Json
import io.circe.generic.JsonCodec
import io.circe.generic.extras.{Configuration, ConfiguredJsonCodec, JsonKey}

object codecs {

  implicit val config: Configuration = Configuration.default.withSnakeCaseMemberNames
  @ConfiguredJsonCodec case class PayloadType(@JsonKey("type") payloadType: String)
  @ConfiguredJsonCodec case class TriggerId(trigger_id: String)
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
