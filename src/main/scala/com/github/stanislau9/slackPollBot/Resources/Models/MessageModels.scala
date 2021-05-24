package com.github.stanislau9.slackPollBot.Resources.Models

import io.circe.Json
import io.circe.generic.extras.{Configuration, ConfiguredJsonCodec}

object MessageModels {

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

  implicit val config: Configuration =
    Configuration.default.withSnakeCaseMemberNames.withSnakeCaseConstructorNames.withDiscriminator("type")

  @ConfiguredJsonCodec final case class View(Title: Title,
                                             submit: Submit,
                                             close: Close,
                                             blocks: List[BlockMember],
                                             `type`: String = "modal")

  @ConfiguredJsonCodec final case class Title(`type`: String = "plain_text",
                                              text: String = "Poll",
                                              emoji: Boolean = true)

  @ConfiguredJsonCodec final case class Submit(`type`: String = "plain_text",
                                               text: String = "Submit",
                                               emoji: Boolean = true)

  @ConfiguredJsonCodec final case class Close(`type`: String = "plain_text",
                                              text: String = "CLose",
                                              emoji: Boolean = true)

  @ConfiguredJsonCodec sealed trait BlockMember
  final case class Actions(elements: List[Button], `type`: String = "actions")     extends BlockMember
  final case class Input(element: Element, label: Label, `type`: String = "input") extends BlockMember

  @ConfiguredJsonCodec final case class Button(text: Text, value: String, actionId: String, `type`: String = "button")
  @ConfiguredJsonCodec final case class Text(text: String, `type`: String = "plain_text", emoji: Boolean = true)

  @ConfiguredJsonCodec final case class Element(actionId: String,
                                                initialValue: String = "",
                                                `type`: String = "plain_text_input")

  @ConfiguredJsonCodec final case class Label(text: String, `type`: String = "plain_text", emoji: Boolean = true)

  @ConfiguredJsonCodec final case class Modal(view: MData)
  @ConfiguredJsonCodec final case class MData(blocks: List[BlockMember], state: State)
  @ConfiguredJsonCodec final case class State(values: Map[String, Map[String, Value]])
  @ConfiguredJsonCodec final case class Value(value: Option[String])

  @ConfiguredJsonCodec final case class Section(text: Text,
                                                blockId: String,
                                                accessory: Button,
                                                `type`: String = "section")

  @ConfiguredJsonCodec final case class Message(message: MessageData)
  @ConfiguredJsonCodec final case class MessageData(blocks: List[Section])
  @ConfiguredJsonCodec final case class ActionsCheck(actions: List[Button])

}
