package com.github.stanislau9.slackPollBot.Resources.Models

import io.circe.generic.extras.ConfiguredJsonCodec
import io.circe.generic.extras.Configuration

object MessageModels {

  implicit val config: Configuration =
    Configuration.default.withSnakeCaseMemberNames.withSnakeCaseConstructorNames.withDiscriminator("type")

  @ConfiguredJsonCodec sealed trait Response

  @ConfiguredJsonCodec final case class OpenModal(triggerId: String, view: View)                        extends Response
  @ConfiguredJsonCodec final case class UpdateModal(viewId: String, view: View)                         extends Response
  @ConfiguredJsonCodec final case class PostMessage(channel: String, blocks: List[Block])               extends Response
  @ConfiguredJsonCodec final case class UpdateMessage(channel: String, ts: String, blocks: List[Block]) extends Response

  @ConfiguredJsonCodec final case class View(Title: Title,
                                             submit: Submit,
                                             close: Close,
                                             blocks: List[Block],
                                             `type`: String = "modal")
  @ConfiguredJsonCodec final case class Title(text: String, `type`: String = "plain_text")
  @ConfiguredJsonCodec final case class Submit(text: String = "Submit", `type`: String = "plain_text")
  @ConfiguredJsonCodec final case class Close(text: String = "Close", `type`: String = "plain_text")

  @ConfiguredJsonCodec sealed trait Block
  @ConfiguredJsonCodec final case class Input(element: Element, label: Label)                     extends Block
  @ConfiguredJsonCodec final case class Actions(elements: List[Elements])                         extends Block
  @ConfiguredJsonCodec final case class Section(text: Text, blockId: String, accessory: Elements) extends Block
  @ConfiguredJsonCodec final case class Header(text: Text)                                        extends Block

  @ConfiguredJsonCodec sealed trait Elements

  @ConfiguredJsonCodec final case class Button(text: Text, value: String, actionId: String) extends Elements
  @ConfiguredJsonCodec final case class ConversationsSelect(placeholder: Placeholder,
                                                            actionId: String,
                                                            initialConversation: String = "U020YFE1ZPD")
      extends Elements

  @ConfiguredJsonCodec final case class Placeholder(text: String = "Select a conversation",
                                                    `type`: String = "plain_text")
  @ConfiguredJsonCodec final case class Element(actionId: String,
                                                initialValue: String = "",
                                                `type`: String = "plain_text_input")
  @ConfiguredJsonCodec final case class Label(text: String, `type`: String = "plain_text")
  @ConfiguredJsonCodec final case class Text(text: String, `type`: String = "plain_text")

  object Update {
    @ConfiguredJsonCodec final case class PayloadUpdate(view: ViewUpdate, actions: List[ButtonUpdate])
    @ConfiguredJsonCodec final case class PayloadSend(view: ViewUpdate)
    @ConfiguredJsonCodec final case class PayloadMessage(message: Mess, actions: List[ButtonUpdate])
    @ConfiguredJsonCodec final case class Mess(blocks: List[Block])
    @ConfiguredJsonCodec final case class ViewUpdate(Title: Title,
                                                     submit: Submit,
                                                     close: Close,
                                                     blocks: List[Block],
                                                     state: State,
                                                     `type`: String = "modal")
    @ConfiguredJsonCodec final case class State(values: Map[String, Map[String, Value]])
    @ConfiguredJsonCodec final case class Value(value: Option[String])
    @ConfiguredJsonCodec final case class ButtonUpdate(actionId: String, value: String)

  }

}
