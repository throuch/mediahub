package canalplus.mediahub.application.http.movie

import akka.actor.ActorRef
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpResponse, StatusCodes}
import akka.util.ByteString
import canalplus.mediahub.application.service.MovieService
import canalplus.mediahub.interfaces.swagger.converter.JsonSupport
import thorn.core.application.http.HttpCommon

import scala.concurrent.ExecutionContext

class MoviePrincipals(movieService: MovieService)
                     (implicit val ec: ExecutionContext) extends HttpCommon  {

  val route =
    path("principalsForMovieName" / Segment) {

      movieName : String =>
        get {

          complete(
            HttpEntity(
              ContentTypes.`text/plain(UTF-8)`,
              // transform each number to a chunk of bytes
              movieService.principalsForMovieName(movieName).map(n => ByteString(s"$n\n"))
            )
          )
        }
    }

}

