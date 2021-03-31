package canalplus.mediahub.application.service.impl

import akka.stream.scaladsl.Source
import canalplus.mediahub.application.service.MovieService
import canalplus.mediahub.domain.entities.MovieService
import canalplus.mediahub.domain.entities.MovieService.{Principal, TvSeries}
import canalplus.mediahub.domain.repositories.{TitlePrincipalsRepositories, TvSeriesRepositories}

trait MovieServiceImpl extends MovieService {
  self : TitlePrincipalsRepositories with TvSeriesRepositories =>

  private def normalizeMovieName(movie: String): String = movie.trim.toLowerCase()

  override def principalsForMovieName(name: String): Source[MovieService.Principal, _] = {
    // TODO filter by movieName, combine
    logger.debug(s"Looking for movie $name ...")
    val titleId = titleRefTable.get(normalizeMovieName(name))
    titleId.fold(Source.empty[MovieService.Principal])( tconst =>
      getTitlePrincipalsRawStream.filter(t=> !t.knownForTitles.intersect(tconst).isEmpty).map(t =>
      MovieService.Principal(t.primaryName, t.birthYear, t.deathYear, t.primaryProfession )))
  }


  override def tvSeriesWithGreatestNumberOfEpisodes(): Source[MovieService.TvSeries, _] = {
    // alternative: groupby (parentTconst, season) => max by episode_num => groupby (parentTconst) => SUM max episode number

    logger.debug(s"Looking for top-ten episode count TV series ...")
    val topTenSeries =parseEpisodes().toList.groupBy(_.parentTconst).mapValues(_.size).
      toList.sortBy(-_._2).take(10).map(_._1).flatMap(x ⇒ showRefTableById.get(x))

    Source.fromIterator(() ⇒ topTenSeries.iterator)

  }
}
