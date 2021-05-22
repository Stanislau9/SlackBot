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

  @ConfiguredJsonCodec final case class Text(`type`: String = "plain_text",
                                             text: String = "CLICK HERE",
                                             emoji: Boolean = true)

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
  @ConfiguredJsonCodec final case class State(values: Json)

  def viewConstructor(requestViewBody: Json = Json.Null): Json = {

    def input(int: Int): Input    = Input(Element(s"input$int"), Label(text = s"Option $int"))
    def action(int: Int): Actions = Actions(List(Button(Text(text = "Add option"), s"$int", "addOption")))
    def viewBody(inputs: List[Input], action: Actions): View =
      View(Title(), Submit(), Close(), inputs ++ List(action))

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

    if (requestViewBody == Json.Null) {
      val i: Input   = input(1)
      val a: Actions = action(1)
      viewBody(List(i), a).asJson
    } else {
      var inputs: List[Input] = List(input(1))
      for (i <- 2 to (countOfInputs + 1)) {
        inputs = inputs ++ List(input(i))
      }
      val a: Actions = action(countOfInputs + 1)
      viewBody(inputs, a).asJson
    }

  }

}
