package canalplus.mediahub.application

import canalplus.mediahub.domain.repositories.{TitlePrincipalsRepositories, TvSeriesRepositories}
import org.scalatest.{Matchers, WordSpec}

class TestRepo extends WordSpec with Matchers {
  object RepoInstance extends TitlePrincipalsRepositories

  object RepoTVInstance extends TvSeriesRepositories

  "Repository" should {
    "load data" in {

      val it = RepoTVInstance.parseEpisodes()

      it.foreach( _ ⇒ ())
//      while( it.hasNext) {
//        it.next()
//      }
    }
  }


  "Repository" should {
    "load title principals" in {

      RepoInstance.parseNameBasics().foreach( _ ⇒ ())

      //RepoInstance.parseTitlePrincipals.foreach( _ ⇒ ())
    }
  }

  "Repository" should {
    "provide titles refs " in {

      println(RepoInstance.titleRefTable.getOrElse("Matrix", "Non trouvé"))
      println(RepoInstance.titleRefTable.getOrElse("matrix", "Non trouvé"))
      println(RepoInstance.titleRefTable.getOrElse("MATRIX", "Non trouvé"))

      println(RepoInstance.titleRefTable.getOrElse("Carmencita","Non trouvé"))
    }
  }

  "Repository" should {
    "provide person refs " in {

      //RepoInstance.personRefTable.filterKeys(_.toLowerCase.contains("keanu")).foreach(x⇒println(x._2))

    }
  }

}
