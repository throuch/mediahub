package canalplus.mediahub.application.http.game

import akka.http.scaladsl.model.StatusCodes
import thorn.core.application.http.HttpCommon
import thorn.core.domain.repositories.ScoreRecord
import canalplus.mediahub.application.injection.GameApplicationMixing
import canalplus.mediahub.interfaces.swagger.converter.JsonSupport
import canalplus.mediahub.interfaces.swagger.game.GameAPI


/**
 * Expected output:
 * {"player": <number of win>, "computer": <number of win>}
 *
 *
 */
class GetResults(implicit appContext: GameApplicationMixing) extends HttpCommon with GameAPI with JsonSupport {

  case class ScoreResponse(player: Int, computer: Int)

  implicit val responseFormat = jsonFormat2(ScoreResponse)

  val route = results

  override def results =
    get {
      pathPrefix("results") {
        pathEndOrSingleSlash {

          val ScoreRecord(_, computerWins, humanWins) =
            appContext.getScoreView(
              appContext.defaultMatchID)
          complete(StatusCodes.OK, ScoreResponse(humanWins, computerWins))
        }
      }
    }
}