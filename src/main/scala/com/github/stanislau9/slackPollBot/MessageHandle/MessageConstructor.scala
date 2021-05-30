package com.github.stanislau9.slackPollBot.MessageHandle

import com.github.stanislau9.slackPollBot.Resources.Models.MessageModels.Update._
import com.github.stanislau9.slackPollBot.Resources.Models.MessageModels._
import io.circe.{DecodingFailure, Json}
object MessageConstructor {
  def openView(triggerId: String): Option[Response] = {

    val button     = Button(Text("Add option"), "0", "AddOption")
    val inputTheme = Input(Element("theme"), Label("Question description"))
    val inputChat  = Input(Element("conversation"), Label("Conversation"))
    val input      = Input(Element("1"), Label("1 option"))
    val actions    = Actions(List(button))
    val emptyView  = View(Title("Poll"), Submit(), Close(), List(inputTheme, inputChat, input, actions))
    Some(OpenModal(triggerId, emptyView))

  }

  def updateView(viewId: String, payload: Json): Option[Response] = {

    val state: Map[String, String] =
      payload
        .as[PayloadUpdate]
        .map(value => value.view.state.values.map { case (_, v) => v }.flatten.toMap)
        .getOrElse(Map.empty)
        .map { case (c, v) => (c, v.value.getOrElse("")) }

    val blocks: Either[DecodingFailure, List[Block]] = payload.as[PayloadUpdate].map(value => value.view.blocks)

    def updateInputs(state: Map[String, String],
                     blocks: Either[DecodingFailure, List[Block]]): Either[DecodingFailure, List[Block]] = {
      val count: Int = state.values.toList.length - 1
      blocks
        .map(list =>
          list.map {
            case value: Input if (state.contains(value.element.actionId)) =>
              value.copy(element = value.element.copy(initialValue = state.getOrElse(value.element.actionId, "")))
            case block => block
        })
        .map(list => list.dropRight(1) ++ List(Input(Element(s"$count"), Label(s"$count option"))) ++ list.takeRight(1))
    }
    val view: View = View(Title("Poll"), Submit(), Close(), updateInputs(state, blocks).getOrElse(List.empty))

    Some(UpdateModal(viewId, view))
  }

  def sendMessage(payload: Json): Option[Response] = {

    val state: Map[String, String] =
      payload
        .as[PayloadSend]
        .map(value => value.view.state.values.map { case (_, v) => v }.flatten.toMap)
        .getOrElse(Map.empty)
        .map { case (c, v) => (c, v.value.getOrElse("")) }
    val options: List[Section] = state
      .-("theme")
      .-("conversation")
      .map { case (k, v) => Section(Text(v), k, Button(Text("Vote"), "0", k)) }
      .toList
    val header: Header      = Header(Text(state.getOrElse("theme", "")))
    val channel: String     = state.getOrElse("conversation", "U020YFE1ZPD")
    val blocks: List[Block] = List(header) ++ options
    Some(PostMessage(channel, blocks))

  }

  def updateMessage(messageTs: String, channelId: String, payload: Json): Option[Response] = {
    val value: String =
      payload.as[PayloadMessage].map(value => value.actions.map(value => value.value).head).getOrElse("0")
    val id: String =
      payload.as[PayloadMessage].map(value => value.actions.map(value => value.actionId).head).getOrElse("ID")
    val valueBut = (value.toInt + 1).toString
    val updatedBlocks: Either[DecodingFailure, List[Block]] = payload
      .as[PayloadMessage]
      .map(value =>
        value.message.blocks.map {
          case Input(element, label) => Input(element, label)
          case Actions(elements)     => Actions(elements)
          case Section(text, blockId, accessory) =>
            Section(
              text,
              blockId,
              accessory match {
                case value: Button if value.actionId == id =>
                  value.copy(text = value.text.copy(s"$valueBut Vote"), value = valueBut)
                case Button(text, value, actionId) => Button(text, value, actionId)
                case ConversationsSelect(placeholder, actionId, initialConversation) =>
                  ConversationsSelect(placeholder, actionId, initialConversation)
              }
            )
          case Header(text) => Header(text)
      })

    Some(UpdateMessage(channelId, messageTs, updatedBlocks.getOrElse(List())))
  }

  def updateMinusMessage(messageTs: String, channelId: String, payload: Json): Option[Response] = {
    val value: String =
      payload.as[PayloadMessage].map(value => value.actions.map(value => value.value).head).getOrElse("0")
    val id: String =
      payload.as[PayloadMessage].map(value => value.actions.map(value => value.actionId).head).getOrElse("ID")
    val valueBut = (value.toInt - 1).toString
    val updatedBlocks: Either[DecodingFailure, List[Block]] = payload
      .as[PayloadMessage]
      .map(value =>
        value.message.blocks.map {
          case Input(element, label) => Input(element, label)
          case Actions(elements)     => Actions(elements)
          case Section(text, blockId, accessory) =>
            Section(
              text,
              blockId,
              accessory match {
                case value: Button if value.actionId == id =>
                  value.copy(text = value.text.copy(s"$valueBut Vote"), value = valueBut)
                case Button(text, value, actionId) => Button(text, value, actionId)
                case ConversationsSelect(placeholder, actionId, initialConversation) =>
                  ConversationsSelect(placeholder, actionId, initialConversation)
              }
            )
          case Header(text) => Header(text)
      })

    Some(UpdateMessage(channelId, messageTs, updatedBlocks.getOrElse(List())))
  }

}
