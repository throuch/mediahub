package canalplus.mediahub.application.service

import akka.stream.scaladsl.Source
import canalplus.mediahub.domain.entities.MovieService.{Principal, TvSeries}


trait MovieService {
  def principalsForMovieName(name: String): Source[Principal, _]

  def tvSeriesWithGreatestNumberOfEpisodes(): Source[TvSeries, _]


}