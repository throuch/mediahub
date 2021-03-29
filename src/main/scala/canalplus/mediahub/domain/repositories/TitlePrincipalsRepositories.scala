package canalplus.mediahub.domain.repositories

import com.typesafe.scalalogging.LazyLogging

import java.util.zip.GZIPInputStream
import scala.io.{Source => IOsource}

trait TitlePrincipalsRepositories extends LazyLogging {

  import TitlePrincipalsRepositories._
  private[this] type MovieName = String
  private[this] type tconst= String
  private[this] type nconst= String

  //  - https://datasets.imdbws.com/name.basics.tsv.gz
//  name.basics.tsv.gz – Contains the following information for names:
//
//    nconst (string) - alphanumeric unique identifier of the name/person
//    primaryName (string)– name by which the person is most often credited
//  birthYear – in YYYY format
//  deathYear – in YYYY format if applicable, else '\N'
//  primaryProfession (array of strings)– the top-3 professions of the person
//  knownForTitles (array of tconsts) – titles the person is known for


  //https://datasets.imdbws.com/title.basics.tsv.gz

  //  https://datasets.imdbws.com/title.principals.tsv.gz
  //  title.principals.tsv.gz – Contains the principal cast/crew for titles
  //
  //  tconst (string) - alphanumeric unique identifier of the title
  //  ordering (integer) – a number to uniquely identify rows for a given titleId
  //  nconst (string) - alphanumeric unique identifier of the name/person
  //  category (string) - the category of job that person was in
  //  job (string) - the specific job title if applicable, else '\N'
  //  characters (string) - the name of the character played if applicable, else '\N'

  /**
   *
   * @return (primaryName,birthYear,deathYear,primaryProfession,knownForTitles)
   */
  def getTitlePrincipalsRawStream = {
    akka.stream.scaladsl.Source.fromIterator(()=> parseNameBasics())
  }

//  // TODO
//  def getNameBasicsRawStream = {
//    akka.stream.scaladsl.Source.empty
//  }

  /**
   *
   * @return entity NameBasics encapsulating : primaryName,birthYear,deathYear,primaryProfession,knownForTitles
   *         (titles are id)
   */
  def parseNameBasics(): Iterator[NameBasics] = {
     val stream =IOsource.fromInputStream(
      new GZIPInputStream( classOf[TitlePrincipalsRepositories].getResourceAsStream("/name.basics.tsv.gz")))

    // display header for debug
    //stream.getLines().take(5).toList.foreach(s=>logger.debug(s))

    stream.getLines.drop(1).map(_.split("\t")).
      collect( { case  Array(_,primaryName,birthYear,deathYear,primaryProfession,knownForTitles) if birthYear != "\\N" =>
         NameBasics(primaryName, birthYear.toInt
           , deathYear match { case "\\N" => None; case a => Some(a.toInt)},
           primaryProfession.split(",").toList, knownForTitles.split(",").toList)})

  }

  /**
   * Construit un tableau d'association des films avec comme clé le titre en minuscule et en valeur son ID IMDB
   *
   * @return
   */
  def buildTitleRefTable(): Map[MovieName, List[tconst]] = {
    val stream =IOsource.fromInputStream(
      new GZIPInputStream( classOf[TitlePrincipalsRepositories].getResourceAsStream("/title.basics.tsv.gz")))

    // display header for debug
    //stream.getLines/*.filter(_.contains("movie"))*/.filter(x ⇒ x.contains("tt0102685") || x.contains("tt0102685")  || x.contains("tt0234215") || x.contains("tt0133093") || x.contains("tt0111257")).take(5000).toList.foreach(s=>logger.debug(s))

    stream.reset().getLines.drop(1).map(_.split("\t")).
      collect( { case  Array(id,titleType,primaryTitle,_,_,_,_,_,_)  if titleType == "movie" => primaryTitle.toLowerCase -> id
        }).toList.groupBy(_._1).mapValues(_.map(_._2))
 }

  lazy val titleRefTable : Map[MovieName, List[tconst]] = buildTitleRefTable()


//  def buildPersonRefTable(): Map[String, NameBasics] = {
//    parseNameBasics().map( t ⇒ t.primaryName → t).toMap
// }
//
//  lazy val personRefTable : Map[String, NameBasics] = buildPersonRefTable()




  /**
   *
   * @return TBD
   */
//  def parseTitlePrincipals(): Iterator[Any] = {
//    val stream =IOsource.fromInputStream(
//      new GZIPInputStream( classOf[TitlePrincipalsRepositories].getResourceAsStream("/title.principals.tsv.gz")))
//
//    // display header for debug
//    stream.getLines().take(2).toList.foreach(s=>logger.debug(s))
//
//    stream.getLines.drop(1).map(_.split("\t")).
//      collect( { case  a @ Array(tconst,_,nconst,_,_,_)  =>
//        Array(tconst, nconst)})
//
//  }
}

object TitlePrincipalsRepositories {
  case class NameBasics(primaryName: String,birthYear: Int,deathYear:Option[Int],primaryProfession: List[String],knownForTitles:List[String])

}
