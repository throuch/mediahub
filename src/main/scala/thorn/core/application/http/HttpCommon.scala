package thorn.core.application.http

import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives
import akka.util.Timeout
import org.slf4j.LoggerFactory

import scala.concurrent.duration._

trait HttpCommon extends Directives {

  implicit val timeout = Timeout(5 seconds)

  val log = LoggerFactory.getLogger(getClass)

  def completeJson(json: String) = complete(HttpResponse(entity = HttpEntity(ContentTypes.`application/json`, json)))

  def completeJson(status: StatusCode, json: String) =
    complete(HttpResponse(status, entity = HttpEntity(ContentTypes.`application/json`, json)))

}
