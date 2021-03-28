package canalplus.mediahub.application.service

import akka.stream.scaladsl.Source
import canalplus.mediahub.domain.entities.MovieService.{Principal, TvSeries}


trait MovieService {
  def principalsForMovieNameStream(name: String): Source[Principal, _]

  def tvSeriesWithGreatestNumberOfEpisodesStream(): Source[TvSeries, _]

  //def asyncPrincipalsForMovieName(name: String): Future[Seq[Principal]]

  //def asyncTvSeriesWithGreatestNumberOfEpisodes(): Future[Seq[TvSeries]]

}