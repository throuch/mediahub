package canalplus.mediahub.application

import canalplus.mediahub.domain.repositories.TitlePrincipalsRepositories
import org.scalatest.{Matchers, WordSpec}

class TestRepo extends WordSpec with Matchers {
  "Repository" should {
    "load data" in {
        object RepoInstance extends TitlePrincipalsRepositories

      RepoInstance.load()
    }
  }


}
