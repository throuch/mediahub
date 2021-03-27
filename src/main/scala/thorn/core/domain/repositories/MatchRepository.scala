package thorn.core.domain.repositories

import thorn.core.interfaces.{MatchID, PlayerID}


case class ScoreRecord(matchId: MatchID, computerScore: Int = 0, humanScore: Int = 0)

case class MatchObjectValue(matchId: MatchID,
                            computerScore: Int = 0,
                            humanScore: Int = 0,
                            humanPlayerId: PlayerID /* to be completed */)


/**
 * stores information about matches and scores in particular
 *
 * TODO think about what is about service and what is about pure repository
 *
 */
trait MatchRepository {


  def count: Int

  def delete(matchId: MatchID): Unit

  // def getOrCreate(matchId: MatchID = UUID.randomUUID()): MatchObjectValue

  def get(matchId: MatchID): Option[MatchObjectValue]

  def put(matchId: MatchID, value: MatchObjectValue): Unit

  def exists(matchId: MatchID): Boolean
}
