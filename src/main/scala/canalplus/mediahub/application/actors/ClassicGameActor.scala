package canalplus.mediahub.application.actors

import akka.actor.{Actor, ActorLogging, Props}
import thorn.core.application.DomainConverter
import thorn.core.application.service.MatchService
import thorn.core.interfaces.MatchID
import canalplus.mediahub.application.injection.ComputerAI
import canalplus.mediahub.interfaces.swagger.model.{GameAction, GameActionResponse}
import canalplus.rps.domain.ClassicGame
import canalplus.rps.infrastructure.InMemoryGameRecorder
import canalplus.rps.interfaces.RPSElement.RPSElement

import scala.collection.mutable


/**
 *
 *
 */
class ClassicGameActor(appContext: MatchService with ComputerAI) extends Actor with ActorLogging {


  override def receive: Receive = {
    case (matchId: MatchID, body: GameAction) =>
      sender() ! onHumanAction(getGameReference(matchId), body)
    case msg ⇒ log.warning(s"DEBUG: unrecognized message $msg")
  }

  def createClassicGame(matchId: MatchID): ClassicGame = {
    val `match` = appContext.read(matchId)
    val game = new ClassicGame(`match`, InMemoryGameRecorder, appContext.defaultGameStrategy)
    if (!`match`.isMatchReadyToStart)
      throw new IllegalStateException
    game
  }

  def getGameReference(matchId: MatchID): ClassicGame = {
    games.getOrElseUpdate(matchId, createClassicGame(matchId))
  }

  def formatMatchResult(
                         computerElement: RPSElement,
                         humanElement: RPSElement,
                         humanResultWin: Boolean): String = {
    val result = if (humanResultWin) "win" else "lose"
    s"You played $humanElement, I played $computerElement, you $result"
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

object ClassicGameActor {
  def props(appContext: MatchService with ComputerAI): Props =
    Props(new ClassicGameActor(appContext))

}
