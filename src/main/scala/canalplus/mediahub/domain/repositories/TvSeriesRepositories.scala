package canalplus.mediahub.domain.repositories

import canalplus.mediahub.domain.entities.MovieService.TvSeries
import com.typesafe.scalalogging.LazyLogging

import java.util.zip.GZIPInputStream
import scala.io.{Source ⇒ IOsource}

/**
 * This class will provide some  TV series data
 */
trait TvSeriesRepositories  extends  LazyLogging {
    import TvSeriesRepositories._

  private[this] type tconst =String
  private[this] type TvShowName = String


//  tconst (string) - alphanumeric unique identifier of the title
//    titleType (string) – the type/format of the title (e.g. movie, short, tvseries, tvepisode, video, etc)
//  primaryTitle (string) – the more popular title / the title used by the filmmakers on promotional materials at the point of release
//  originalTitle (string) - original title, in the original language
//    isAdult (boolean) - 0: non-adult title; 1: adult title
//    startYear (YYYY) – represents the release year of a title. In the case of TV Series, it is the series start year
//    endYear (YYYY) – TV Series end year. ‘\N’ for all other title types
//    runtimeMinutes – primary runtime of the title, in minutes
//    genres (string array) – includes up to three genres associated with the title
//

  /**
   *
   * @return
   */
  def getEpisodesRawStream = {
    akka.stream.scaladsl.Source.fromIterator(()=> parseEpisodes())
  }

  def parseEpisodes(): Iterator[Episodes] = {
    val stream =IOsource.fromInputStream(
      new GZIPInputStream( getClass.getResourceAsStream("/title.episode.tsv.gz")))

    // display header for debug
    val (debugIt, it) = stream.getLines.duplicate
    //debugIt.take(10).toList.foreach(s=>logger.debug(s))

    it.drop(1).map(_.split("\t")). // drop CSV header
      collect( { case Array(tconst,parentTconst,seasonNumber,episodeNumber) /*if (seasonNumber != "\\N") && (episodeNumber != "\\N")*/ =>
        Episodes(tconst,parentTconst, if (seasonNumber=="\\N") 0 else seasonNumber.toInt,if (episodeNumber=="\\N") 0 else episodeNumber.toInt)})
  }

  /**
   * TBD
   *
   * @return
   */
  def buildShowRefTable(): Map[tconst, TvSeries] = {
    val stream =IOsource.fromInputStream(
      new GZIPInputStream( classOf[TitlePrincipalsRepositories].getResourceAsStream("/title.basics.tsv.gz")))

    // display header for debug
    val (debugIt, it) = stream.getLines.duplicate
    //logger.debug("buildShowRefTable")
    //debugIt.take(10).toList.foreach(s=>logger.debug(s))

    it.drop(1).map(_.split("\t")). // drop CSV header
      collect( { case a@ Array(id,titleType,primaryTitle,_,_,startYear,endYear,_,genres)  if titleType == "tvSeries" || titleType == "tvEpisodes" =>
        id -> TvSeries(primaryTitle, startYear match { case "\\N" ⇒ 0; case v ⇒ v.toInt}, endYear match { case "\\N" ⇒ None; case v ⇒ Some(v.toInt)}, genres.split(",").toList)
      }).toMap
  }

  lazy val showRefTableById : Map[tconst, TvSeries] = buildShowRefTable()

}

object TvSeriesRepositories {
  case class Episodes(tconst:String,parentTconst:String, seasonNumber:Int, episodeNumber :Int)
}
