package canalplus.mediahub.domain.repositories

import canalplus.mediahub.domain.entities.MovieService.TvSeries
import com.typesafe.scalalogging.LazyLogging

import java.util.zip.GZIPInputStream
import scala.io.{Source ⇒ IOsource}

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
// combine to have years and gender list
  }

  /**
   * @TODO
   * @return
   */
//  def getTitleBasicsRawStream = {
//    akka.stream.scaladsl.Source.empty
//  }


  def parseEpisodes(): Iterator[Episodes] = {
    val stream =IOsource.fromInputStream(
      new GZIPInputStream( classOf[TitlePrincipalsRepositories].getResourceAsStream("/title.episode.tsv.gz")))

    // display header for debug
    stream.getLines().take(2).toList.foreach(s=>logger.debug(s))

    stream.getLines.drop(1).map(_.split("\t")).
      collect( { case Array(tconst,parentTconst,seasonNumber,episodeNumber) if (seasonNumber != "\\N") && (episodeNumber != "\\N") =>
        Episodes(tconst,parentTconst,seasonNumber.toInt,episodeNumber.toInt)})
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
    stream.getLines.filter(_.contains("tvserie")).take(5000).toList.foreach(s=>logger.debug(s))//.filter(x ⇒ x.contains("tt0102685") || x.contains("tt0102685")  || x.contains("tt0234215") || x.contains("tt0133093") || x.contains("tt0111257")).take(5000).toList.foreach(s=>logger.debug(s))

    stream.reset().getLines.drop(1).map(_.split("\t")).
      collect( { case  Array(id,titleType,primaryTitle,_,_,startYear,endYear,_,genres)  if titleType == "tvserie"  =>
        id -> TvSeries(primaryTitle, startYear.toInt, endYear match { case "\\N" ⇒ None; case v ⇒ Some(v.toInt)}, genres.split(",").toList)
      }).toMap
  }

  lazy val showRefTableById : Map[tconst, TvSeries] = buildShowRefTable()

}

object TvSeriesRepositories {
  case class Episodes(tconst:String,parentTconst:String, seasonNumber:Int, episodeNumber :Int)
}
