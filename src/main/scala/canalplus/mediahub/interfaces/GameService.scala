package canalplus.mediahub.interfaces

import java.time.LocalDate

import canalplus.core.interfaces._

/**
 *
 * The general service allowing to manage different games
 *
 */
trait GameService {


  /**
   * create a new Rock-Paper-Scissors game
   *
   * @param
   * @return
   */
  def createRockPaperScissorsGame(config: GameConfiguration): MatchID

  /**
   * register one or several human players to an existing match
   *
   * @param matchID
   * @param playerID
   *
   * return an exception if the expected number of players is wrong
   */
  def registerHumanPlayers(matchID: MatchID, playerID: PlayerID*): Unit

  /**
   * create a new player
   * return an exception if the player is under 18 years
   **/
  def createHumanPlayer(pseudo: String, birthdate: LocalDate): PlayerID


}
