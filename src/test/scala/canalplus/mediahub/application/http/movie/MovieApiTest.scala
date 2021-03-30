package canalplus.mediahub.application.http.movie

import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives.complete
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.util.ByteString
import canalplus.mediahub.application.injection.Module
import canalplus.mediahub.interfaces.swagger.converter.JsonSupport
import org.scalatest.{Matchers, WordSpec}
import org.slf4j.LoggerFactory
import akka.http.scaladsl.server._
import Directives._

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

  def postRequest(path: String, json: ByteString): HttpRequest =
    HttpRequest(HttpMethods.POST,
      uri = path,
      entity = HttpEntity(MediaTypes.`application/json`, json)
    )


  object WebServiceToTest extends Module

  val smallroute = Route.seal(
    new MoviePrincipals(WebServiceToTest).route ~
      new TopTenEpisodesCountSeries(WebServiceToTest).route )

  "The service" should {
    "return " in {

      Get("/principalsForMovieName/moviename?Titanic") ~> smallroute ~> check {


        entityAs[String] shouldEqual "true"

      }
    }
  }

  "The service" should {
    "return " in {

      Get("/tvSeriesWithGreatestNumberOfEpisodes") ~> smallroute ~> check {


        entityAs[String] shouldEqual "true"
      }
    }
  }



}
