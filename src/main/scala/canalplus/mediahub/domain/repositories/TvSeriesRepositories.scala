package canalplus.mediahub.domain.repositories

import canalplus.mediahub.domain.entities.MovieService.Principal

import scala.collection.mutable

trait TvSeriesRepositories {
  //  - https://datasets.imdbws.com/title.basics.tsv.gz
  //  - https://datasets.imdbws.com/title.episode.tsv.gz
  type TvSeriesId = String

  val db: mutable.Map[TvSeriesId, Principal]= mutable.HashMap()

}
