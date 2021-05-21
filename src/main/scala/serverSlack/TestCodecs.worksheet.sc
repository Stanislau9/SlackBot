import cats.syntax.functor._
import io.circe.{ Codec, Decoder, Encoder }
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto._
import io.circe.syntax._

sealed trait Container

case class Message(messageTs: String, channelId: String) extends Container
case class Message2(x: String) extends Container

final case class Body(container: Container)

object GenericDerivation {
  implicit val genDevConfig: Configuration = Configuration.default.withDiscriminator("type")

  implicit val msgCodec: Codec[Message] = deriveConfiguredCodec[Message]
  implicit val msg2Codec: Codec[Message2] = deriveConfiguredCodec[Message2]
  implicit val containerCodec: Codec[Container] = deriveConfiguredCodec[Container]

  implicit val bodyCodec: Codec[Body] = deriveConfiguredCodec[Body]
}

import GenericDerivation._

val msg = Message("messageTs123", "channelId456")
val msg2 = Message2("x9")
Body(msg).asJson.noSpaces
Body(msg2).asJson.noSpaces
