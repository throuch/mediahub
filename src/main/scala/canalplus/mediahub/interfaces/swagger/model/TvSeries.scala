package canalplus.mediahub.interfaces.swagger.model

import io.swagger.annotations.ApiModel

@ApiModel(description = "TvSeries")
final case class TvSeries(
                           original: String,
                           startYear: Int,
                           endYear: Option[Int],
                           genres: List[String])
