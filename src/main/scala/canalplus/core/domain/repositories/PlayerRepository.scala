package canalplus.core.domain.repositories

import canalplus.core.domain.entities.HumanPlayer
import canalplus.core.interfaces.PlayerID

import scala.collection.mutable

trait PlayerRepository {
  val players: mutable.Map[PlayerID, HumanPlayer]
}
