package canalplus.core.application

import canalplus.rps.domain.{ClassicElement, Paper, Rock, Scissors}
import canalplus.rps.interfaces.RPSElement

object DomainConverter {

  def toDomain(e: RPSElement.RPSElement): ClassicElement = {
    e match {
      case RPSElement.`rock` => Rock
      case RPSElement.`scissors` => Scissors
      case RPSElement.`paper` => Paper
      case _ => throw new IllegalArgumentException
    }

  }

  def toApi(e: ClassicElement): RPSElement.RPSElement = {
    e match {
      case Rock ⇒ RPSElement.`rock`
      case Scissors ⇒ RPSElement.`scissors`
      case Paper ⇒ RPSElement.`paper`
      case _ => throw new IllegalArgumentException
    }


  }
}
