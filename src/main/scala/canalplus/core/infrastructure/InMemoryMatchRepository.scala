package canalplus.core.infrastructure


import canalplus.core.domain.repositories.{MatchObjectValue, MatchRepository, ScoreRecord}
import canalplus.core.interfaces.MatchID

import scala.collection.mutable

trait InMemoryMatchRepository extends MatchRepository {

  val matchDb = mutable.HashMap[MatchID, MatchObjectValue]()


  override def count: Int = matchDb.size

  //override def get(matchId: MatchID): Option[MatchObjectValue] = matchDb.get(matchId)

  // override def put(value: MatchObjectValue): Unit = matchDb + (value.matchId → value)


  override def delete(matchId: MatchID): Unit = {
    matchDb.remove(matchId)
  }


  //  override def getOrCreate(id: MatchID): MatchObjectValue =
  //    matchDb.getOrElseUpdate(id, MatchObjectValue(id))
  override def get(matchId: MatchID): Option[MatchObjectValue] = matchDb.get(matchId)

  override def put(matchId: MatchID, value: MatchObjectValue): Unit = matchDb.put(matchId, value)

  override def exists(matchId: MatchID): Boolean = matchDb.contains(matchId)
}
