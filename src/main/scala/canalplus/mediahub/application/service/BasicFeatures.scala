package canalplus.mediahub.application.service

import java.time.LocalDate

import thorn.core.interfaces.PlayerType.{Computer, Human}
import thorn.core.interfaces.{GameConfiguration, MatchID, PlayerID}
import canalplus.mediahub.interfaces.GameService

trait BasicFeatures {
  self: GameService ⇒

  var defaultMatchID: MatchID = createNewMatch()
  val defaultPlayerID = self.createHumanPlayer("Default User", LocalDate.parse("1977-05-30"))
  self.registerHumanPlayers(defaultMatchID, defaultPlayerID)


  def resetDefaultMatch() =
    defaultMatchID = createNewMatch()


  private def createNewMatch(): MatchID = self.createRockPaperScissorsGame(GameConfiguration(Human, Computer))

}
