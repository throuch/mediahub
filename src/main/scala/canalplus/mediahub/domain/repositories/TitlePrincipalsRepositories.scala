package canalplus.mediahub.domain.repositories

import canalplus.mediahub.domain.entities.MovieService.Principal
import com.typesafe.scalalogging.LazyLogging
import scala.io.Source

import java.io.FileInputStream
import java.net.{URI, URL}
import java.util.zip.GZIPInputStream
import scala.collection.mutable

trait TitlePrincipalsRepositories extends LazyLogging {
  //  - https://datasets.imdbws.com/name.basics.tsv.gz
  //  - https://datasets.imdbws.com/title.principals.tsv.gz
  type MovieId = String
  type PrincipalId = String

  val db: mutable.Map[PrincipalId, Principal]= mutable.HashMap()

  def load(): Unit = {
    val readmeText  = parseNameBasics()

      Source.fromIn
    readmeText.take(10).foreach( m=> logger.debug( m._1 ))
  }

  def parseNameBasics(): Iterator[Tuple5[String,Int, Option[Int], List[String], List[String]]] = {
     val stream =Source.fromInputStream(
      new GZIPInputStream( classOf[TitlePrincipalsRepositories].getResourceAsStream("/name.basics.tsv.gz")))
       logger.debug(stream.getLines().take(1).toList.head)
       stream.
      getLines.drop(1).map(_.split("\t")).
       map( { case a @ Array(_,primaryName,birthYear,deathYear,primaryProfession,knownForTitles) =>
         (primaryName, birthYear.toInt, deathYear match { case "\\N" => None; case a => Some(a.toInt)},
           primaryProfession.split(",").toList, knownForTitles.split(",").toList)})

  }

}
