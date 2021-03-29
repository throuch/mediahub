package canalplus.mediahub.domain.repositories

import com.typesafe.scalalogging.LazyLogging

import java.util.zip.GZIPInputStream
import scala.io.{Source => IOsource}

trait TvSeriesRepositories  extends LazyLogging {
  //  - https://datasets.imdbws.com/title.basics.tsv.gz
  //  - https://datasets.imdbws.com/title.episode.tsv.gz
  type TvSeriesId = String

  import TvSeriesRepositories._

  //val db: mutable.Map[TvSeriesId, Principal]= mutable.HashMap()
  def getEpisodesRawStream = {
    akka.stream.scaladsl.Source.fromIterator(()=> parseEpisodes())
// combine to have years and gender list
  }

  //TODO
  def getTitleBasicsRawStream = {
    akka.stream.scaladsl.Source.empty

  }


  def parseEpisodes(): Iterator[Episodes] = {
    val stream =IOsource.fromInputStream(
      new GZIPInputStream( classOf[TitlePrincipalsRepositories].getResourceAsStream("/title.episode.tsv.gz")))

    // display header for debug
    stream.getLines().take(2).toList.foreach(s=>logger.debug(s))

    stream.getLines.drop(1).map(_.split("\t")).
      collect( { case Array(tconst,parentTconst,seasonNumber,episodeNumber) if (seasonNumber != "\\N") && (episodeNumber != "\\N") =>
        Episodes(tconst,parentTconst,seasonNumber.toInt,episodeNumber.toInt)})
//    Iterator.empty
  }
}

object TvSeriesRepositories {
  case class Episodes(tconst:String,parentTconst:String, seasonNumber:Int, episodeNumber :Int)
}
