package canalplus.mediahub.interfaces.swagger.model

import io.swagger.annotations.ApiModel


@ApiModel(description = "Scala model containing an Enumeration Value")
case class Principal(
                       name: String,
                       birthYear: Int,
                       deathYear: Option[Int],
                       profession: List[String])


