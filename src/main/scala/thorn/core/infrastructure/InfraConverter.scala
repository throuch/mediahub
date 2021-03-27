package thorn.core.infrastructure

import thorn.core.domain.entities.Match
import thorn.core.domain.repositories.MatchObjectValue

object InfraConverter {


  def toInfra(m: Match): MatchObjectValue = {
    MatchObjectValue(m.id, m.homeScore, m.visitorScore, m.getVisitorPlayer.id)
  }
}
