package canalplus.mediahub.application.http.movie

import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives.complete
import akka.http.scaladsl.testkit.{RouteTestTimeout, ScalatestRouteTest}
import canalplus.mediahub.application.injection.Module
import canalplus.mediahub.interfaces.swagger.converter.JsonSupport
import org.scalatest.{Matchers, WordSpec}
import org.slf4j.LoggerFactory
import akka.http.scaladsl.server._
import Directives._
import akka.testkit.TestDuration

import scala.concurrent.duration._

class MovieApiTest extends WordSpec with Matchers with ScalatestRouteTest with JsonSupport {
  val log = LoggerFactory.getLogger(getClass)


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

  implicit val timeout = RouteTestTimeout(30.seconds dilated)

  object WebServiceToTest extends Module

  val smallroute = Route.seal(
    new MoviePrincipals(WebServiceToTest).route ~
      new TopTenEpisodesCountSeries(WebServiceToTest).route )

  "The service" should {
    "return the crew of the film Titanic" in {

      Get("/principalsForMovieName?moviename=The%20Matrix") ~> smallroute ~> check {

        val lines = responseAs[String].split("\n")
        lines(0) shouldEqual "Principal(Keanu Reeves,1964,None,List(actor, producer, soundtrack))"

      }
    }
  }

  "The service" should {
    "return top-ten TV series with the greatest number of episodes" in {

      Get("/tvSeriesWithGreatestNumberOfEpisodes") ~> smallroute ~> check {

        val lines = responseAs[String].split("\n")
        lines(0) shouldEqual "TvSeries(Days of Our Lives,1965,None,List(Drama, Romance))"

      }
    }
  }



}
