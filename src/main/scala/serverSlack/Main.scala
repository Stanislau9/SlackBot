package serverSlack

import cats.effect.{ExitCode, IO, IOApp}

object Main extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = {
    val service: MessageService[IO] = MessageService[IO]
    for {
      _ <- Server.startWith(service)
    } yield ExitCode.Success
  }
}
