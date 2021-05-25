package com.github.stanislau9.slackPollBot

import io.circe.{DecodingFailure, Json}
import io.circe.syntax._
//import com.github.stanislau9.slackPollBot.Resources.Models.MessageModels._
import com.github.stanislau9.slackPollBot.Resources.Models.MessageModels2._
import com.github.stanislau9.slackPollBot.Resources.Models.MessageModels2.Update._
object MessageConstructor {
  def openView(triggerId: String): Option[Response] = {

    val button     = Button(Text("Add option"), "VAL", "AddOption")
    val inputTheme = Input(Element("theme"), Label("Question"))
    val inputChat  = Input(Element("conversation"), Label("Conversation"))
    val input      = Input(Element("1"), Label("1 option"))
    //val select     = ConversationsSelect(Placeholder(), "SelectChat")
    val actions   = Actions(List(button))
    val emptyView = View(Title("Poll"), Submit(), Close(), List(inputTheme, inputChat, input, actions))
    Some(OpenModal(triggerId, emptyView))
    //Some(View.Open(triggerId, viewConstructor()))
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
    // Some(View.Update(viewId, viewConstructor(payload)))
    Some(UpdateModal(viewId, view))
  }
//  def updateMessage(messageTs: String, channelId: String, payload: Json): Option[Response] = {
//    Some(Chat.Update(channelId, messageTs, updatePoll(payload)))
//  }
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
      .map { case (k, v) => Section(Text(v), k, Button(Text("Vote"), "VAL", k)) }
      .toList
    val header: Header      = Header(Text(state.getOrElse("theme", "")))
    val channel: String     = state.getOrElse("conversation", "U020YFE1ZPD")
    val blocks: List[Block] = List(header) ++ options
    Some(PostMessage(channel, blocks))
//    Some(Chat.Post("chat-bot", PollConstructor(payload)))

  }
//
//  private def viewConstructor(requestViewBody: Json = Json.Null): Json = {
//
//    def input(int: Int, initialValue: String = ""): Input =
//      Input(Element(s"input$int", initialValue), Label(text = s"Option $int"))
//    def action(int: Int): Actions = Actions(List(Button(Text("Add option"), s"$int", "addOption")))
//    def viewBody(inputs: List[Input], action: Actions): View =
//      View(Title(), Submit(), Close(), inputs ++ List(action))
//
//    if (requestViewBody == Json.Null) {
//      val i: List[Input] = List(input(1), input(2))
//      val a: Actions     = action(2)
//      viewBody(i, a).asJson
//    } else {
//
//      val data = requestViewBody.as[Modal]
//
//      val countOfInputs: Int = data match {
//        case Left(value) => ??? //add error
//        case Right(value) =>
//          value.view.blocks
//            .filter(blockMember =>
//              blockMember match {
//                case _: Actions => true
//                case _: Input   => false
//            })
//            .map {
//              case value: Actions => value.elements.map(_.value.toInt).head
//              case value: Input   => ??? //add error
//            }
//            .head
//      }
//      val previousInputs: List[Input] = data
//        .map(_.view.state.values.map { case (_, v) => v }.flatten.toMap.map {
//          case (actionId, v) => input(actionId.takeRight(1).toInt, v.value.getOrElse(""))
//        }.toList)
//        .getOrElse(List())
//
//      val a: Actions = action(countOfInputs + 1)
//      viewBody(previousInputs ++ List(input(countOfInputs + 1)), a).asJson
//    }
//
//  }
//
//  private def PollConstructor(requestViewBody: Json): List[Json] = {
//
//    def section(initialValue: String = ""): Section =
//      Section(Text(initialValue), initialValue, Button(Text("0 (0%) Vote"), "0", initialValue))
//
//    val data = requestViewBody.as[Modal]
//
//    val sections: List[Section] = data
//      .map(_.view.state.values.map { case (_, v) => v }.flatten.toMap.map {
//        case (actionId, v) => section(v.value.getOrElse(""))
//      }.toList)
//      .getOrElse(List())
//
//    sections.map(_.asJson)
//  }
//
//  private def updatePoll(requestMessageBody: Json = Json.Null): List[Json] = {
//    val blocks = requestMessageBody.as[Message].map(_.message.blocks)
//
//    val action = requestMessageBody.as[ActionsCheck].map(actCheck => actCheck.actions.head)
//    val id     = action.map(_.actionId).getOrElse("notId")
//    val value  = (action.map(_.value).getOrElse("notVal").toInt + 1).toString
//
//    val changedBlocks = blocks.map(list =>
//      list.map(section =>
//        section.blockId match {
//          case valueId if (valueId == id) =>
//            section.copy(accessory = section.accessory match {
//              case button =>
//                button.copy(button.text match {
//                  case text => text.copy(text = s"$value Vote")
//                }, value, button.actionId, button.`type`)
//            })
//          case _ => section
//      }))
//
//    changedBlocks.getOrElse(List()).map(_.asJson)
//
//  }
}
