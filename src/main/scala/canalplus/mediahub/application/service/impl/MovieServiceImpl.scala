package canalplus.mediahub.application.service.impl

import akka.stream.Materializer

import java.net.URI
import java.nio.file.Path
import java.nio.file.Paths
import akka.stream.scaladsl.{Compression, FileIO, Sink, Source}
import canalplus.mediahub.application.service.MovieService
import canalplus.mediahub.domain.entities.MovieService

import scala.concurrent.{Await, ExecutionContext}
import scala.concurrent.duration.DurationInt

trait MovieServiceImpl extends MovieService {
//  - https://datasets.imdbws.com/name.basics.tsv.gz
//  - https://datasets.imdbws.com/title.ratings.tsv.gz
//
 implicit val mat: Materializer

  override def principalsForMovieName(name: String): Source[MovieService.Principal, _] = {
    Source.empty
  }

  override def tvSeriesWithGreatestNumberOfEpisodes(): Source[MovieService.TvSeries, _] = {
    //val chemin = Paths.get(URI.create("https://datasets.imdbws.com/name.basics.tsv.gz"))
    //Await.result(FileIO.fromPath(chemin).via(Compression.gunzip()).map(_.utf8String).runWith(Sink.head), 20.seconds)

    Source.empty
  }
}
