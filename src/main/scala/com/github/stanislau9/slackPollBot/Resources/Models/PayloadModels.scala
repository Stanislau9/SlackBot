package com.github.stanislau9.slackPollBot.Resources.Models

import io.circe.generic.extras.{Configuration, ConfiguredJsonCodec}

object PayloadModels {

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
