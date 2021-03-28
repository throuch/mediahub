package canalplus.mediahub.interfaces

import canalplus.mediahub.interfaces.swagger.model.{Principal, TvSeries}


trait MovieWebService {
  def principalsForMovieName(name: String): Seq[Principal]

  def tvSeriesWithGreatestNumberOfEpisodes(): Seq[TvSeries]
}
