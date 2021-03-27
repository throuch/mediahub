package canalplus.mediahub.application.actors

import akka.actor.{Actor, ActorLogging, Props}
import thorn.core.application.DomainConverter
import thorn.core.application.service.MatchService
import thorn.core.interfaces.MatchID
import canalplus.mediahub.application.injection.ComputerAI
import canalplus.mediahub.application.service.MovieService
import canalplus.mediahub.interfaces.swagger.model.{GameAction, GameActionResponse}
import canalplus.rps.domain.ClassicGame
import canalplus.rps.infrastructure.InMemoryGameRecorder
import canalplus.rps.interfaces.RPSElement.RPSElement

import scala.collection.mutable


/**
 *
 *
 */
class MediaHubActor(appContext: MovieService) extends Actor with ActorLogging {


  override def receive: Receive = {
    case (matchId: MatchID, body: GameAction) =>
      sender() ! onHumanAction(getGameReference(matchId), body)
    case msg ⇒ log.warning(s"DEBUG: unrecognized message $msg")
  }



  def onHumanAction(game: ClassicGame, action: GameAction): GameActionResponse = {

    val (humanHand, computerHand, _, humanResultWin) =
      game.playRound(DomainConverter.toDomain(action.myHand))

    GameActionResponse(humanResultWin,
      formatMatchResult(DomainConverter.toApi(computerHand),
        DomainConverter.toApi(humanHand),
        humanResultWin))

  }

}

object MediaHubActor {
  def props(appContext: MovieService): Props =
    Props(new MediaHubActor(appContext))

}
