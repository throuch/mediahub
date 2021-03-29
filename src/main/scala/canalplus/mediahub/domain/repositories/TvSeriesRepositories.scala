package canalplus.mediahub.domain.repositories

import com.typesafe.scalalogging.LazyLogging

import java.util.zip.GZIPInputStream
import scala.io.{Source => IOsource}

trait TvSeriesRepositories  extends LazyLogging {
  //  - https://datasets.imdbws.com/title.basics.tsv.gz
  //  - https://datasets.imdbws.com/title.episode.tsv.gz
  type TvSeriesId = String

  //val db: mutable.Map[TvSeriesId, Principal]= mutable.HashMap()
  def getEpisodesRawStream = {
    akka.stream.scaladsl.Source.fromIterator(()=> parseEpisodes())
// combine to have years and gender list
  }

  //TODO
  def getTitleBasicsRawStream = {
    akka.stream.scaladsl.Source.empty

  }


  def parseEpisodes(): Iterator[(String,String, Int, Int)] = {
    val stream =IOsource.fromInputStream(
      new GZIPInputStream( classOf[TitlePrincipalsRepositories].getResourceAsStream("/title.episode.tsv.gz")))

    // display header for debug
    stream.getLines().take(2).toList.foreach(s=>logger.debug(s))

    stream.getLines.drop(1).map(_.split("\t")).
      map( { case a @ Array(tconst,parentTconst,seasonNumber,episodeNumber) =>
        (tconst,parentTconst,seasonNumber.toInt,episodeNumber.toInt)})
//    Iterator.empty
  }
}
