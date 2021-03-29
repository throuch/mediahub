
package canalplus.mediahub.application.http


import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import akka.http.scaladsl.server.{MalformedRequestContentRejection, Rejection, RejectionHandler, Route}
import akka.stream.{ActorMaterializer, Materializer}
import canalplus.mediahub.application.http.movie.{MoviePrincipals, TopTenEpisodesCountSeries}
import canalplus.mediahub.application.injection.Module
import canalplus.mediahub.interfaces.swagger.SwaggerDocService
import ch.megard.akka.http.cors.scaladsl.CorsDirectives.cors
import org.slf4j.LoggerFactory
import thorn.core.application.http.common.{Ping, Site, Status}

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

class HttpServer(implicit system: ActorSystem, appContext: Module, m: Materializer) extends Site {
  val log = LoggerFactory.getLogger(getClass)

  implicit def myRejectionHandler =
    RejectionHandler.newBuilder()
      .handle {
        case _: MalformedRequestContentRejection => {
          complete(HttpResponse(StatusCodes.Forbidden, entity = "Invalid body !"))
        }
        case e : Rejection => complete(HttpResponse(StatusCodes.ImATeapot, entity = e.toString))
      }
      .handleNotFound {
        complete(HttpResponse(StatusCodes.InternalServerError))
      }
      .result()


  implicit val ec: ExecutionContext = system.dispatcher

  val route =
    cors()(
      Route.seal(
        SwaggerDocService.routes ~
          new Ping().route ~
          new Status().route ~
          new MoviePrincipals(appContext).route ~
          new TopTenEpisodesCountSeries(appContext).route ~
          site)
    )

  val port = sys.env("ADVERTISED_PORT").toShort

  val bindingFuture = Http().bindAndHandle(route, "0.0.0.0", port)

//  val bindingFuture = Http().newServerAt("localhost", 8080).bind(route)
//
//  println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
//  StdIn.readLine()
//  bindingFuture.flatMap(_.unbind()).onComplete(_ => system.terminate())

  bindingFuture.
    onComplete {
      case Success(bound) =>
        log.info(s"Server online at http://${bound.localAddress.getHostString}:${bound.localAddress.getPort}/")
      case Failure(e) =>
        log.error(s"Server could not start!", e)
        system.terminate()
    }


}

