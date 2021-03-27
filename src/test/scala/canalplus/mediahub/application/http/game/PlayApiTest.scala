package canalplus.mediahub.application.http.game

import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives.{complete, _}
import akka.http.scaladsl.server.{RejectionHandler, Route, _}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.util.ByteString
import thorn.core.interfaces.MatchID
import canalplus.mediahub.application.injection.GameApplicationMixing
import canalplus.mediahub.interfaces.swagger.converter.JsonSupport
import canalplus.mediahub.interfaces.swagger.model.{GameAction, GameActionResponse}
import canalplus.rps.application.actors.ClassicGameActor
import canalplus.rps.domain.{AIStrategy, ClassicElement, Paper}
import canalplus.rps.interfaces.RPSElement
import org.scalatest.{Matchers, WordSpec}
import org.slf4j.LoggerFactory

class PlayApiTest extends WordSpec with Matchers with ScalatestRouteTest with JsonSupport {
  val log = LoggerFactory.getLogger(getClass)

  implicit val element = enumFormat(RPSElement)
  implicit val requestFormat = jsonFormat1(GameAction)
  implicit val responseFormat = jsonFormat2(GameActionResponse)

  /*
  implicit def myExceptionHandler: ExceptionHandler =
    ExceptionHandler {
      case e: Exception =>
        e.printStackTrace()
        complete(HttpResponse(StatusCodes.InternalServerError, entity = "Error in test writing"))
    }
*/
  implicit val myRejectionHandler =
    RejectionHandler.newBuilder()
      .handle {
        case _ =>

          complete(HttpResponse(StatusCodes.Forbidden, entity = "Invalid body !"))

      }
      .handleNotFound {
        complete(HttpResponse(StatusCodes.InternalServerError))
      }
      .result()

  def postRequest(path: String, json: ByteString): HttpRequest =
    HttpRequest(HttpMethods.POST,
      uri = path,
      entity = HttpEntity(MediaTypes.`application/json`, json)
    )


  implicit val instance = new GameApplicationMixing {

    override val defaultGameStrategy: AIStrategy = new AIStrategy {
      override def getHand(matchId: MatchID): ClassicElement = Paper
    }
  }


  val gameActorRef = system.actorOf(ClassicGameActor.props(instance), "GameActor")
  val play = new Play(gameActorRef)
  val result = new GetResults()

  val smallroute = Route.seal(
    play.route ~
      result.route)

  "The service" should {
    "return 418 for POST requests to /play with rock" in {

      Post("/play", GameAction(RPSElement.rock)) ~> smallroute ~> check {

        status.intValue() should equal(418)
        entityAs[String].startsWith("You played rock, I played paper") shouldEqual true

      }
    }
  }

  "The service" should {
    "return 200 for POST requests to /play with scissors" in {

      Post("/play", GameAction(RPSElement.scissors)) ~> smallroute ~> check {

        status.intValue() should equal(200)
        entityAs[String].startsWith("You played scissors, I played paper") shouldEqual true
      }
    }
  }

  "The service" should {
    "return 418 for POST requests to /play with paper" in {

      Post("/play", GameAction(RPSElement.paper)) ~> smallroute ~> check {

        status.intValue() should equal(418)
        entityAs[String] shouldEqual "You played paper, I played paper, you lose"
      }
    }
  }


  "The service" should {
    "return 403 for POST requests to /play with incorrect hand value" in {
      postRequest("/play", ByteString("""{ "myHand" : "crap" }""")) ~> smallroute ~> check {

        status should ===(StatusCodes.Forbidden)
        log.info(response.toString)

      }


    }
  }

  "The service" should {
    "return ??? /results" in {

      Get("/results") ~> smallroute ~> check {

        log.info("TEST DEBUG: " + entityAs[String])
        entityAs[String] shouldEqual """{"computer":2,"player":1}"""
      }
    }
  }

}
