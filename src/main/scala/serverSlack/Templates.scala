package serverSlack

import io.circe.{Json, parser}

object Templates {
  val block = Array(parser.parse("""{
                                   |			"type": "section",
                                   |			"text": {
                                   |				"type": "mrkdwn",
                                   |				"text": "This is a section block with a button."
                                   |			},
                                   |			"accessory": {
                                   |				"type": "button",
                                   |				"text": {
                                   |					"type": "plain_text",
                                   |					"text": "Click Me",
                                   |					"emoji": true
                                   |				},
                                   |				"value": "click_me_123",
                                   |				"action_id": "button-action"
                                   |			}
                                   |		}""".stripMargin).getOrElse(Json.Null))

  val blockEdited = Array(parser.parse("""{
                                         |			"type": "section",
                                         |			"text": {
                                         |				"type": "mrkdwn",
                                         |				"text": "This is a section edited block with a button."
                                         |			},
                                         |			"accessory": {
                                         |				"type": "button",
                                         |				"text": {
                                         |					"type": "plain_text",
                                         |					"text": "Click Me",
                                         |					"emoji": true
                                         |				},
                                         |				"value": "click_me_123",
                                         |				"action_id": "button-action"
                                         |			}
                                         |		}""".stripMargin).getOrElse(Json.Null))

  val v =
    parser.parse("""{
                   |	"type": "modal",
                   |	"title": {
                   |		"type": "plain_text",
                   |		"text": "My App",
                   |		"emoji": true
                   |	},
                   |	"submit": {
                   |		"type": "plain_text",
                   |		"text": "Submit",
                   |		"emoji": true
                   |	},
                   |	"close": {
                   |		"type": "plain_text",
                   |		"text": "Cancel",
                   |		"emoji": true
                   |	},
                   |	"blocks": [
                   |		{
                   |			"type": "section",
                   |			"text": {
                   |				"type": "mrkdwn",
                   |				"text": ":sushi: *Ace Wasabi Rock-n-Roll Sushi Bar*\nThe best landlocked sushi restaurant."
                   |			},
                   |			"accessory": {
                   |				"type": "button",
                   |				"text": {
                   |					"type": "plain_text",
                   |					"emoji": true,
                   |					"text": "Vote"
                   |				},
                   |				"value": "click_me_1"
                   |			}
                   |		},
                   |		{
                   |			"type": "context",
                   |			"elements": [
                   |				{
                   |					"type": "image",
                   |					"image_url": "https://api.slack.com/img/blocks/bkb_template_images/profile_1.png",
                   |					"alt_text": "Michael Scott"
                   |				},
                   |				{
                   |					"type": "image",
                   |					"image_url": "https://api.slack.com/img/blocks/bkb_template_images/profile_2.png",
                   |					"alt_text": "Dwight Schrute"
                   |				},
                   |				{
                   |					"type": "image",
                   |					"image_url": "https://api.slack.com/img/blocks/bkb_template_images/profile_3.png",
                   |					"alt_text": "Pam Beasely"
                   |				},
                   |				{
                   |					"type": "plain_text",
                   |					"emoji": true,
                   |					"text": "3 votes"
                   |				}
                   |			]
                   |		},
                   |		{
                   |			"type": "section",
                   |			"text": {
                   |				"type": "mrkdwn",
                   |				"text": ":hamburger: *Super Hungryman Hamburgers*\nOnly for the hungriest of the hungry."
                   |			},
                   |			"accessory": {
                   |				"type": "button",
                   |				"text": {
                   |					"type": "plain_text",
                   |					"emoji": true,
                   |					"text": "Vote"
                   |				},
                   |				"value": "click_me_2"
                   |			}
                   |		},
                   |		{
                   |			"type": "context",
                   |			"elements": [
                   |				{
                   |					"type": "image",
                   |					"image_url": "https://api.slack.com/img/blocks/bkb_template_images/profile_4.png",
                   |					"alt_text": "Angela"
                   |				},
                   |				{
                   |					"type": "image",
                   |					"image_url": "https://api.slack.com/img/blocks/bkb_template_images/profile_2.png",
                   |					"alt_text": "Dwight Schrute"
                   |				},
                   |				{
                   |					"type": "plain_text",
                   |					"emoji": true,
                   |					"text": "2 votes"
                   |				}
                   |			]
                   |		},
                   |		{
                   |			"type": "section",
                   |			"text": {
                   |				"type": "mrkdwn",
                   |				"text": ":ramen: *Kagawa-Ya Udon Noodle Shop*\nDo you like to shop for noodles? We have noodles."
                   |			},
                   |			"accessory": {
                   |				"type": "button",
                   |				"text": {
                   |					"type": "plain_text",
                   |					"emoji": true,
                   |					"text": "Vote"
                   |				},
                   |				"value": "click_me_3"
                   |			}
                   |		}
                   |	]
                   |}""".stripMargin).getOrElse(Json.Null)

  val attach = parser.parse("""[
                              |        {
                              |            "text": "Choose a game to play",
                              |            "fallback": "You are unable to choose a game",
                              |            "callback_id": "wopr_game",
                              |            "color": "#3AA3E3",
                              |            "attachment_type": "default",
                              |            "actions": [
                              |                {
                              |                    "name": "game",
                              |                    "text": "Chess",
                              |                    "type": "button",
                              |                    "value": "chess"
                              |                },
                              |                {
                              |                    "name": "game",
                              |                    "text": "Falken's Maze",
                              |                    "type": "button",
                              |                    "value": "maze"
                              |                },
                              |                {
                              |                    "name": "game",
                              |                    "text": "Thermonuclear War",
                              |                    "style": "danger",
                              |                    "type": "button",
                              |                    "value": "war",
                              |                    "confirm": {
                              |                        "title": "Are you sure?",
                              |                        "text": "Wouldn't you prefer a good game of chess?",
                              |                        "ok_text": "Yes",
                              |                        "dismiss_text": "No"
                              |                    }
                              |                }
                              |            ]
                              |        }
                              |    ]""".stripMargin).getOrElse(Json.Null)

  val attach2 = parser.parse("""{ "text" : "this is changed",
                               |"replace_original" : true,
                               |"attachments": [
                               |        {
                               |            "text": "Choose a game to play",
                               |            "fallback": "You are unable to choose a game",
                               |            "callback_id": "wopr_game",
                               |            "color": "#3AA3E3",
                               |            "attachment_type": "default",
                               |            "actions": [
                               |                {
                               |                    "name": "game",
                               |                    "text": "Chess",
                               |                    "type": "button",
                               |                    "value": "chess"
                               |                },
                               |                {
                               |                    "name": "game",
                               |                    "text": "Thermonuclear War",
                               |                    "style": "danger",
                               |                    "type": "button",
                               |                    "value": "war",
                               |                    "confirm": {
                               |                        "title": "Are you sure?",
                               |                        "text": "Wouldn't you prefer a good game of chess?",
                               |                        "ok_text": "Yes",
                               |                        "dismiss_text": "No"
                               |                    }
                               |                }
                               |            ]
                               |        }
                               |    ]
                               |    }""".stripMargin).getOrElse(Json.Null)

  val modalAddPoll = parser.parse("""{
                                    |	"type": "modal",
                                    |	"title": {
                                    |		"type": "plain_text",
                                    |		"text": "My App"
                                    |	},
                                    |	"submit": {
                                    |		"type": "plain_text",
                                    |		"text": "Submit"
                                    |	},
                                    |	"close": {
                                    |		"type": "plain_text",
                                    |		"text": "Cancel"
                                    |	},
                                    |	"blocks": [
                                    |		{
                                    |			"type": "input",
                                    |			"element": {
                                    |				"type": "plain_text_input",
                                    |				"action_id": "plain_text_input-action"
                                    |			},
                                    |			"label": {
                                    |				"type": "plain_text",
                                    |				"text": "1 option"
                                    |			}
                                    |		},
                                    |		{
                                    |			"type": "actions",
                                    |			"elements": [
                                    |				{
                                    |					"type": "button",
                                    |					"text": {
                                    |						"type": "plain_text",
                                    |						"text": "Add option"
                                    |					},
                                    |					"value": "addOption",
                                    |					"action_id": "actionId-1"
                                    |				}
                                    |			]
                                    |		}
                                    |	]
                                    |}""".stripMargin).getOrElse(Json.Null)

  val modalUpdatedPoll = parser.parse("""{
                                        |	"type": "modal",
                                        |	"title": {
                                        |		"type": "plain_text",
                                        |		"text": "My App"
                                        |	},
                                        |	"submit": {
                                        |		"type": "plain_text",
                                        |		"text": "Submit"
                                        |	},
                                        |	"close": {
                                        |		"type": "plain_text",
                                        |		"text": "Cancel"
                                        |	},
                                        |	"blocks": [
                                        |		{
                                        |			"type": "input",
                                        |			"element": {
                                        |				"type": "plain_text_input",
                                        |				"action_id": "plain_text_input-action"
                                        |			},
                                        |			"label": {
                                        |				"type": "plain_text",
                                        |				"text": "1 option"
                                        |			}
                                        |		},
                                        |		{
                                        |			"type": "input",
                                        |			"element": {
                                        |				"type": "plain_text_input",
                                        |				"action_id": "plain_text_input-action"
                                        |			},
                                        |			"label": {
                                        |				"type": "plain_text",
                                        |				"text": "2 option",
                                        |				"emoji": true
                                        |			}
                                        |		},
                                        |		{
                                        |			"type": "actions",
                                        |			"elements": [
                                        |				{
                                        |					"type": "button",
                                        |					"text": {
                                        |						"type": "plain_text",
                                        |						"text": "Add option"
                                        |					},
                                        |					"value": "addOption",
                                        |					"action_id": "actionId-1"
                                        |				}
                                        |			]
                                        |		}
                                        |	]
                                        |}""".stripMargin).getOrElse(Json.Null)
}
