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
    }
  }

}
