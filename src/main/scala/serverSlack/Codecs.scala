package serverSlack

import io.circe.Json
import io.circe.generic.JsonCodec
import io.circe.generic.extras.{Configuration, ConfiguredJsonCodec, JsonKey}

object PayloadType {

  implicit val config: Configuration = Configuration.default.withSnakeCaseMemberNames.withSnakeCaseConstructorNames
    .withDiscriminator("type")

  @ConfiguredJsonCodec sealed trait Payload
  final case class Shortcut(triggerId: String)                    extends Payload
  final case class BlockActions(user: User, container: Container) extends Payload
  final case class ViewSubmission()                               extends Payload

  @ConfiguredJsonCodec final case class User(id: String, username: String)

  @ConfiguredJsonCodec sealed trait Container
  final case class View(viewId: String)                          extends Container
  final case class Message(messageTs: String, channelId: String) extends Container

}

object View {

  implicit val config: Configuration = Configuration.default.withSnakeCaseMemberNames.withSnakeCaseConstructorNames
  @ConfiguredJsonCodec final case class Open(triggerId: String, view: Json)

  @ConfiguredJsonCodec final case class Update(viewId: String, view: Json)
}

object Chat {

  implicit val config: Configuration = Configuration.default.withSnakeCaseMemberNames.withSnakeCaseConstructorNames

  @ConfiguredJsonCodec final case class Post(channel: String, blocks: Array[Json])

  @ConfiguredJsonCodec final case class Update(channel: String, ts: String, blocks: Array[Json])
}
//object Codecs {
//
//  implicit val config: Configuration = Configuration.default.withSnakeCaseMemberNames
//
//  @ConfiguredJsonCodec final case class PayloadType(`type`: String)
//
//  @ConfiguredJsonCodec final case class TriggerId(triggerId: String)
//
//  @ConfiguredJsonCodec final case class CallBackId(callbackId: String)
//
//  @ConfiguredJsonCodec final case class Message(channel: String, text: String = "", blocks: Array[Json] = Array())
//
//  @ConfiguredJsonCodec case class PayloadContainer(container: Container)
//
//  @ConfiguredJsonCodec case class Container(`type`: String, viewId: String)
//  //---------------------------------
////  @ConfiguredJsonCodec case class Container(container: ContainerValue)
////  @ConfiguredJsonCodec case class ContainerValue(@JsonKey("type") t: String, messageTs: String, channelId: String)
//  object chat {
//
//    @JsonCodec case class PostMessage(channel: String, text: String = "", blocks: Array[Json] = Array())
//
//    @JsonCodec case class UpdateMessage(channel: String, ts: String, text: String = "", blocks: Array[Json] = Array())
//  }
//
//  object view {
//    @JsonCodec case class Open(trigger_id: String, view: Json)
//    @JsonCodec case class Update(view_id: String, view: Json)
//  }
//}
