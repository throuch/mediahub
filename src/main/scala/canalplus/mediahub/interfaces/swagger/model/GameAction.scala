package canalplus.mediahub.interfaces.swagger.model

import io.swagger.annotations.{ApiModel, ApiModelProperty}
import canalplus.rps.interfaces.RPSElement.RPSElement

import scala.annotation.meta.field


@ApiModel(description = "Scala model containing an Enumeration Value")
case class GameAction(
                       @(ApiModelProperty@field)(value = "myHand",
                         dataType = "mydatamodels.rps.interfaces.RPSElement$") myHand: RPSElement)

