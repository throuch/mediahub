package canalplus.core.application.service

import java.time.LocalDate

import canalplus.core.domain.entities.{HumanPlayer, Match}
import canalplus.core.domain.repositories.{MatchObjectValue, MatchRepository, PlayerRepository, ScoreRecord}
import canalplus.core.infrastructure.InfraConverter
import canalplus.core.interfaces.PlayerType._
import canalplus.core.interfaces.{GameConfiguration, MatchID, PlayerID}


trait MatchService {
  self: MatchRepository with PlayerRepository ⇒

  trait ReactiveMatch extends Match {
    val service: MatchService

    override def incrementVisitorScore(): Unit = {
      super.incrementVisitorScore()
      service.incrementHumanScore(id)
    }

    override def incrementHomeScore(): Unit = {
      super.incrementHomeScore()
      service.incrementComputerScore(id)
    }
  }

  /**
   * register one or several human players to an existing match
   *
   * @param matchID
   * @param playerID
   * @exception UnsupportedOperationException if the supported number of players is wrong
   * @exception IllegalArgumentException if the player doesn't exist
   */
  def registerHumanPlayers(matchID: MatchID, playerID: PlayerID*): Unit = {
    if (playerID.size == 1) {
      if (!players.contains(playerID(0)))
        throw new IllegalArgumentException(s"Unknown player ID ${playerID(0)}")
      if (!exists(matchID))
        throw new IllegalArgumentException(s"Unknown match ID ${matchID}")

      updateMatchWithPlayers(matchID, playerID(0))

    } else
      throw new UnsupportedOperationException("2 humans players mode not yet supported")
  }

  /**
   * create a new player
   *
   * @exception if the player is under 18 years
   **/
  def createHumanPlayer(pseudo: String, birthDate: LocalDate): PlayerID = {
    val player = new HumanPlayer(pseudo = pseudo, birthDate = birthDate)
    players.put(player.id, player)
    player.id
  }

  /**
   * [READ]
   *
   * @param matchId
   * @return
   */
  def getScoreView(matchId: MatchID): ScoreRecord = {
    get(matchId).map(prev ⇒
      ScoreRecord(matchId, prev.computerScore, prev.humanScore)).getOrElse(ScoreRecord(matchId, -1, -1))
  }

  /**
   * [UPDATE]
   *
   * @param matchId
   */
  def incrementHumanScore(matchId: MatchID): Unit =
    get(matchId).foreach(prev ⇒
      put(matchId, prev.copy(humanScore = prev.humanScore + 1)))

  /**
   * [UPDATE]
   *
   * @param matchId
   */
  def incrementComputerScore(matchId: MatchID): Unit =
    get(matchId).foreach(prev ⇒
      put(matchId, prev.copy(computerScore = prev.computerScore + 1)))


  /**
   *
   * [CREATE]
   * Instanciate a new match and store it in the repository
   *
   * @param config the game configuration
   * @return a match
   */
  def createGame(config: GameConfiguration): Match = {
    val `match` =
      if (((config.playerOne == Human) && (config.playerTwo == Computer)) ||
        ((config.playerOne == Computer) && (config.playerTwo == Human))
      ) {
        new Match(expectHumanPlayersCount = 1) with ReactiveMatch {
          override val service = self
        }
      }
      else if ((config.playerOne == Computer) && (config.playerTwo == Computer)) {
        new Match(expectHumanPlayersCount = 0) with ReactiveMatch {
          override val service = self
        }
      }
      else { // human Vs human
        throw new UnsupportedOperationException("2 humans players mode not yet supported")
        //new Match(roundCount = _config.roundCount, expectHumanPlayersCount = 2)
      }

    store(`match`)
    `match`
  }

  /**
   * [CREATE]
   * store a match entity
   */
  protected def store(m: Match): Unit =
    put(m.id, InfraConverter.toInfra(m))


  /**
   * [READ]
   * get a match entity from the repository
   * retrieve human player
   */
  def read(matchID: MatchID): Match =
    get(matchID).map(m ⇒
      toController(m, players.getOrElse(m.humanPlayerId, throw new Exception(s"Player ${m.humanPlayerId} not found")))
    ).getOrElse(throw new Exception(s"Match $matchID NOT FOUND"))


  /**
   * [UPDATE]
   *
   * @param matchID
   * @param playerID
   * @return
   */
  def updateMatchWithPlayers(matchID: MatchID, playerID: PlayerID) = {
    val v = get(matchID).getOrElse(throw new Exception(s"Match $matchID NOT FOUND"))
    put(matchID, v.copy(humanPlayerId = playerID))
  }

  private def toController(m: MatchObjectValue, playerOne: HumanPlayer): ReactiveMatch = {
    new Match(m.matchId, m.computerScore, m.humanScore, 1) with ReactiveMatch {
      override val service = self
    }.withPlayerOne(playerOne)
  }

}
