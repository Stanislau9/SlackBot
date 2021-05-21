package serverSlack

import cats.effect.{ConcurrentEffect, Sync, Timer}
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.syntax.all._

import scala.concurrent.ExecutionContext

object Server {
  def startWith[F[_]: Sync](messageService: MessageService[F])(implicit concurrent: ConcurrentEffect[F],
                                                               timer: Timer[F]): F[Unit] =
    BlazeServerBuilder[F](ExecutionContext.global)
      .bindHttp(port = Config.port, host = Config.host)
      .withHttpApp(Routes(messageService).routes.orNotFound)
      .serve
      .compile
      .drain
}
