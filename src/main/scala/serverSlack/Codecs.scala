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

  @ConfiguredJsonCodec final case class Post(channel: String, blocks: List[Json])

  @ConfiguredJsonCodec final case class Update(channel: String, ts: String, blocks: List[Json])
}
