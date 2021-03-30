package canalplus.mediahub.application

import canalplus.mediahub.application.service.impl.MovieServiceImpl
import canalplus.mediahub.domain.repositories.{TitlePrincipalsRepositories, TvSeriesRepositories}
import org.scalatest.{Matchers, WordSpec}

class TestServices extends WordSpec with Matchers {
  object RepoInstance extends TitlePrincipalsRepositories

  object RepoTVInstance extends TvSeriesRepositories


  object ServiceToTest extends MovieServiceImpl with  TvSeriesRepositories with TitlePrincipalsRepositories

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


      val resultMatrix = RepoInstance.titleRefTable.get("the matrix")

      println(resultMatrix)

      assertResult("tt0133093")(resultMatrix.fold("NOT FOUND")(_.head ))

    }
  }

  "Service" should {
    "return user expectations " in {


      //ServiceToTest.principalsForMovieName("The Matrix")
      ServiceToTest.tvSeriesWithGreatestNumberOfEpisodes()

      println (s"taille de la base des series = ${RepoTVInstance.showRefTableById.size}")
    }
  }

}
