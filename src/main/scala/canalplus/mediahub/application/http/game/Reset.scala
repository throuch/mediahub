package canalplus.mediahub.application.http.game

import akka.http.scaladsl.model.StatusCodes
import canalplus.core.application.http.HttpCommon
import canalplus.mediahub.application.injection.GameApplicationMixing
import canalplus.mediahub.interfaces.swagger.game.GameAPI

/**
 *
 * Empty Implementation for now
 *
 *
 */
class Reset(implicit appContext: GameApplicationMixing) extends HttpCommon with GameAPI {

  val route = reset

  override def reset =
    get {
      pathPrefix("reset") {
        pathEndOrSingleSlash {

          appContext.resetDefaultMatch()

          complete(StatusCodes.OK)

        }

      }

    }
}