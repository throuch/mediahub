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
    // TODO groupby (parentTconst, season) => max by episode_num => groupby (parentTconst) => SUM max episode number
//    val toto =parseEpisodes().toList.groupBy(t ⇒ (t.parentTconst, t.seasonNumber)).mapValues(_.maxBy(_.episodeNumber)).
//      map(x ⇒ (x._1._1, x._2)).toList.groupBy(t ⇒ t._1).mapValues(x ⇒ x.map(_._2.episodeNumber).sum).
//      toList.sortBy(-_._2).take(10).map(_._1).flatMap(x ⇒ showRefTableById.get(x))
    val toto =parseEpisodes().toList.groupBy(t ⇒ t.parentTconst).mapValues(_.size).
      toList.sortBy(-_._2).take(10).map(_._1).flatMap(x ⇒ showRefTableById.get(x))

//    val toto =parseEpisodes().toList.groupBy(_.parentTconst).mapValues(_.maxBy(_.episodeNumber)).
//      toSeq.sortBy(-_._2.episodeNumber).take(10).map(_._1).flatMap(x ⇒ showRefTableById.get(x))

    logger.debug("Resultats:")
    logger.debug(s"count = ${toto.size}")
    toto.foreach(x⇒ logger.debug(x.toString))

    Source.empty

  }
}
