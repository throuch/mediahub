package canalplus.mediahub.domain.repositories

import com.typesafe.scalalogging.LazyLogging

import java.util.zip.GZIPInputStream
import scala.io.{Source => IOsource}

trait TitlePrincipalsRepositories extends LazyLogging {
  //  - https://datasets.imdbws.com/name.basics.tsv.gz
  //  - https://datasets.imdbws.com/title.principals.tsv.gz
  type MovieId = String
  type PrincipalId = String

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


  def parseNameBasics(): Iterator[(String, Int, Option[Int], List[String], List[String])] = {
     val stream =IOsource.fromInputStream(
      new GZIPInputStream( classOf[TitlePrincipalsRepositories].getResourceAsStream("/name.basics.tsv.gz")))

    // display header for debug
    stream.getLines().take(2).toList.foreach(s=>logger.debug(s))

    stream.getLines.drop(1).map(_.split("\t")).
       map( { case a @ Array(_,primaryName,birthYear,deathYear,primaryProfession,knownForTitles) =>
         (primaryName, 1234 /*Integer.parseInt(birthYear)*/
           , deathYear match { case "\\N" => None; case a => Some(a.toInt)},
           primaryProfession.split(",").toList, knownForTitles.split(",").toList)})

  }

  def parseTitlePrincipals(): Unit = {
    val stream =IOsource.fromInputStream(
      new GZIPInputStream( classOf[TitlePrincipalsRepositories].getResourceAsStream("/title.principals.tsv.gz")))

    // display header for debug
    stream.getLines().take(2).toList.foreach(s=>logger.debug(s))
()
//    stream.getLines.drop(1).map(_.split("\t")).
//      map( { case a @ Array(_,primaryName,birthYear,deathYear,primaryProfession,knownForTitles) =>
//        (primaryName, birthYear.toInt, deathYear match { case "\\N" => None; case a => Some(a.toInt)},
//          primaryProfession.split(",").toList, knownForTitles.split(",").toList)})

  }
}
