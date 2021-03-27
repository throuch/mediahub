package canalplus.core.application.http.common

import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.model.StatusCodes.OK
import akka.http.scaladsl.server.Directives.{complete, get, path}

class Status {
  val route = path("status") {
    get {
      val status = "OK"
      complete(HttpResponse(OK, entity = status))
    }
  }
}