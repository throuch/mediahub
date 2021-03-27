package canalplus.mediahub.application.http.game

import akka.actor.ActorRef
import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import akka.pattern.ask
import canalplus.core.application.http.HttpCommon
import canalplus.mediahub.interfaces.swagger.converter.JsonSupport
import canalplus.mediahub.interfaces.swagger.model.{GameAction, GameActionResponse}
import canalplus.rps.interfaces.RPSElement

import scala.concurrent.ExecutionContext

class Play2(gameactor: ActorRef)
           (implicit val ec: ExecutionContext) extends HttpCommon with JsonSupport {


  implicit val element = enumFormat(RPSElement)
  implicit val requestFormat = jsonFormat1(GameAction)
  implicit val responseFormat = jsonFormat2(GameActionResponse)

  val route = play

  def play =
    path("play" / JavaUUID) {
      match_id ⇒
        post {
          entity(as[GameAction]) { event =>

            complete {
              (gameactor ? (match_id, event)).mapTo[GameActionResponse].
                map(status ⇒ HttpResponse(if (status.humanWins) StatusCodes.OK else StatusCodes.ImATeapot,
                  entity = status.message))
            }
          }
        }
    }
}
