package canalplus.mediahub.application.service.impl

import akka.stream.scaladsl.Source
import canalplus.mediahub.application.service.MovieService
import canalplus.mediahub.domain.entities.MovieService
import canalplus.mediahub.domain.entities.MovieService.{Principal, TvSeries}
import canalplus.mediahub.domain.repositories.{TitlePrincipalsRepositories, TvSeriesRepositories}

trait MovieServiceImpl extends MovieService {
  self : TitlePrincipalsRepositories with TvSeriesRepositories =>

  override def principalsForMovieName(name: String): Source[MovieService.Principal, _] = {
    // TODO filter by movieName, combine
    logger.debug(s"Looking for movie $name ...")
    val titleId = titleRefTable.get(name.trim.toLowerCase())
    titleId.fold(Source.empty[MovieService.Principal])( tconst =>
      getTitlePrincipalsRawStream.filter(t=> !t.knownForTitles.intersect(tconst).isEmpty).map(t =>
      MovieService.Principal(t.primaryName, t.birthYear, t.deathYear, t.primaryProfession )))
  }

  override def tvSeriesWithGreatestNumberOfEpisodes(): Source[MovieService.TvSeries, _] = {
    // TODO groupby (parentTconst, season) => max by episode_num => SUM

    getEpisodesRawStream.map( x=> TvSeries(x.parentTconst,1234,None,List()))
  }
}
