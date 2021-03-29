package canalplus.mediahub.application

import canalplus.mediahub.domain.repositories.{TitlePrincipalsRepositories, TvSeriesRepositories}
import org.scalatest.{Matchers, WordSpec}

class TestRepo extends WordSpec with Matchers {
  object RepoInstance extends TitlePrincipalsRepositories

  object RepoTVInstance extends TvSeriesRepositories

  "Repository" should {
    "load data" in {

      RepoTVInstance.parseEpisodes()
    }
  }


  "Repository" should {
    "load title principals" in {

      RepoInstance.parseTitlePrincipals()
    }
  }

}
