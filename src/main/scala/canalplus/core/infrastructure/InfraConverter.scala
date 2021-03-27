package canalplus.core.infrastructure

import canalplus.core.domain.entities.Match
import canalplus.core.domain.repositories.MatchObjectValue

object InfraConverter {


  def toInfra(m: Match): MatchObjectValue = {
    MatchObjectValue(m.id, m.homeScore, m.visitorScore, m.getVisitorPlayer.id)
  }
}
