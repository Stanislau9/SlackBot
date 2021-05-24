package serverSlack

import io.circe.Decoder.Result
import io.circe.generic.extras.ConfiguredJsonCodec
import io.circe.Json
import io.circe.generic.extras.Configuration
import io.circe.syntax._

object MessageBuilder {
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

  @ConfiguredJsonCodec final case class Button(text: Text, value: String, actionId: String, `type`: String = "button")

  @ConfiguredJsonCodec final case class Text(text: String, `type`: String = "plain_text", emoji: Boolean = true)

  @ConfiguredJsonCodec sealed trait BlockMember

  final case class Actions(elements: List[Button], `type`: String = "actions") extends BlockMember

  final case class Input(element: Element, label: Label, `type`: String = "input") extends BlockMember

  @ConfiguredJsonCodec final case class Element(actionId: String,
                                                initialValue: String = "",
                                                `type`: String = "plain_text_input")

  @ConfiguredJsonCodec final case class Label(`type`: String = "plain_text",
                                              text: String = " LABEL INPUT HERE",
                                              emoji: Boolean = true)
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

  def viewConstructor(requestViewBody: Json = Json.Null): Json = {

    def input(int: Int, initialValue: String = ""): Input =
      Input(Element(s"input$int", initialValue), Label(text = s"Option $int"))
    def action(int: Int): Actions = Actions(List(Button(Text("Add option"), s"$int", "addOption")))
    def viewBody(inputs: List[Input], action: Actions): View =
      View(Title(), Submit(), Close(), inputs ++ List(action))

    if (requestViewBody == Json.Null) {
      val i: List[Input] = List(input(1), input(2))
      val a: Actions     = action(2)
      viewBody(i, a).asJson
    } else {

      val data: Result[Modal] = requestViewBody.as[Modal]

      val countOfInputs: Int = data match {
        case Left(value) => ??? //add error
        case Right(value) =>
          value.view.blocks
            .filter(blockMember =>
              blockMember match {
                case _: Actions => true
                case _: Input   => false
            })
            .map {
              case value: Actions => value.elements.map(_.value.toInt).head
              case value: Input   => ??? //add error
            }
            .head
      }
      val previousInputs: List[Input] = data
        .map(_.view.state.values.map { case (_, v) => v }.flatten.toMap.map {
          case (actionId, v) => input(actionId.takeRight(1).toInt, v.value.getOrElse(""))
        }.toList)
        .getOrElse(List())

      val a: Actions = action(countOfInputs + 1)
      viewBody(previousInputs ++ List(input(countOfInputs + 1)), a).asJson
    }

  }

  def PollConstructor(requestViewBody: Json): List[Json] = {

    def section(initialValue: String = ""): Section =
      Section(Text(initialValue), initialValue, Button(Text("0 (0%) Vote"), "0", initialValue))

    val data: Result[Modal] = requestViewBody.as[Modal]

    val sections: List[Section] = data
      .map(_.view.state.values.map { case (_, v) => v }.flatten.toMap.map {
        case (actionId, v) => section(v.value.getOrElse(""))
      }.toList)
      .getOrElse(List())

    sections.map(_.asJson)
  }

  def updatePoll(requestMessageBody: Json = Json.Null): List[Json] = {
    val blocks = requestMessageBody.as[Message].map(_.message.blocks)

    val action = requestMessageBody.as[ActionsCheck].map(actCheck => actCheck.actions.head)
    val id     = action.map(_.actionId).getOrElse("notId")
    val value  = (action.map(_.value).getOrElse("notVal").toInt + 1).toString

    val changedBlocks = blocks.map(list =>
      list.map(section =>
        section.blockId match {
          case valueId if (valueId == id) =>
            section.copy(accessory = section.accessory match {
              case button =>
                button.copy(button.text match {
                  case text => text.copy(text = s"$value Vote")
                }, value, button.actionId, button.`type`)
            })
          case _ => section
      }))

    changedBlocks.getOrElse(List()).map(_.asJson)

  }
}
