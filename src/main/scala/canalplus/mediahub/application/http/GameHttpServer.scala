
package canalplus.mediahub.application.http


import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import akka.http.scaladsl.server.{MalformedRequestContentRejection, RejectionHandler, Route}
import akka.stream.ActorMaterializer
import ch.megard.akka.http.cors.scaladsl.CorsDirectives.cors
import canalplus.core.application.http.common.{Ping, Site, Status}
import canalplus.mediahub.application.http.game.{GetResults, Play, Reset}
import canalplus.mediahub.application.injection.GameApplicationMixing
import canalplus.mediahub.interfaces.swagger.SwaggerDocService
import org.slf4j.LoggerFactory

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

class GameHttpServer(game: ActorRef)(implicit system: ActorSystem, appContext: GameApplicationMixing) extends Site {
  val log = LoggerFactory.getLogger(getClass)

  implicit def myRejectionHandler =
    RejectionHandler.newBuilder()
      .handle {
        case _: MalformedRequestContentRejection => {
          complete(HttpResponse(StatusCodes.Forbidden, entity = "Invalid body !"))
        }
      }
      .handleNotFound {
        complete(HttpResponse(StatusCodes.InternalServerError))
      }
      .result()


  implicit val ec: ExecutionContext = system.dispatcher
  implicit val m: ActorMaterializer = ActorMaterializer()


  val route =
    cors()(
      Route.seal(
        SwaggerDocService.routes ~
          new Ping().route ~
          new Status().route ~
          new Play(game).route ~
          new GetResults().route ~
          new Reset().route ~
          site)
    )

  val port = sys.env("ADVERTISED_PORT").toShort

  val bindingFuture = Http().bindAndHandle(route, "0.0.0.0", port)

  bindingFuture.
    onComplete {
      case Success(bound) =>
        log.info(s"Server online at http://${bound.localAddress.getHostString}:${bound.localAddress.getPort}/")
      case Failure(e) =>
        log.error(s"Server could not start!", e)
        system.terminate()
    }


}

