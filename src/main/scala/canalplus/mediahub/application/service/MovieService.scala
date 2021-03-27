package canalplus.mediahub.application.service

import akka.stream.scaladsl.Source


trait MovieService {
  def principalsForMovieName(name: String): Source[Principal, _]

  def tvSeriesWithGreatestNumberOfEpisodes(): Source[TvSeries, _]
}