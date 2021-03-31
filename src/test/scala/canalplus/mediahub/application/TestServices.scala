package canalplus.mediahub.application

import canalplus.mediahub.application.service.impl.MovieServiceImpl
import canalplus.mediahub.domain.repositories.{TitlePrincipalsRepositories, TvSeriesRepositories}
import org.scalatest.{Matchers, WordSpec}

class TestServices extends WordSpec with Matchers {
  object RepoInstance extends TitlePrincipalsRepositories

  object RepoTVInstance extends TvSeriesRepositories


  object ServiceToTest extends MovieServiceImpl with  TvSeriesRepositories with TitlePrincipalsRepositories

  "TvSeriesRepositories" should {
    "not throw exception when parsing episodes data" in {

      val it = RepoTVInstance.parseEpisodes()

      it.foreach( _ ⇒ ())

    }
  }


  "TitlePrincipalsRepositories" should {
    "not throw exception when parsing principals data" in {

      RepoInstance.parseNameBasics().foreach( _ ⇒ ())

    }
  }

  "Repository" should {
    "provide titles refs " in {

      val resultMatrix = RepoInstance.titleRefTable.get("the matrix")

      println(resultMatrix)

      println(RepoTVInstance.showRefTableById.values.filter(_.original.toLowerCase.contains("simpsons")))

      assertResult("tt0133093")(resultMatrix.fold("NOT FOUND")(_.head ))

    }
  }

  "Service" should {
    "return user expectations " in {

          println(s"taille de la base des series = ${RepoTVInstance.showRefTableById.size}")
         assertResult(204215 )(RepoTVInstance.showRefTableById.size)
    }
  }

}
