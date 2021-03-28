package canalplus.mediahub.application.service.impl

import akka.stream.scaladsl.Source
import canalplus.mediahub.application.service.MovieService
import canalplus.mediahub.domain.entities.MovieService

trait MovieServiceImpl extends MovieService {
  override def principalsForMovieName(name: String): Source[MovieService.Principal, _] = {
    ???
  }

  override def tvSeriesWithGreatestNumberOfEpisodes(): Source[MovieService.TvSeries, _] = {
    ???
  }
}
