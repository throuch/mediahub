package canalplus.mediahub.application.http.movie

import akka.actor.ActorRef
import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import canalplus.mediahub.interfaces.swagger.converter.JsonSupport
import thorn.core.application.http.HttpCommon

import scala.concurrent.ExecutionContext

class MoviePrincipals(mediaActor: ActorRef)
                     (implicit val ec: ExecutionContext) extends HttpCommon  {

  val route = play

  def play =
    path("principalsForMovieName" / String) {
      name ⇒
        get {
            movieName=>

            complete {
              (mediaActor ? GetPrincipals(movieName)).mapTo[Seq[Principal]].map(_.fromDomain).
                map(message ⇒ HttpResponse(StatusCodes.OK,
                  entity = message))
            }
          }
        }
    }
}

