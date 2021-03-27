package canalplus.mediahub.interfaces.swagger.game


import akka.http.scaladsl.server.Route
import io.swagger.annotations._
import akka.http.scaladsl.server.Directives.complete
import canalplus.mediahub.interfaces.swagger.model.{Principal, TvSeries}

import javax.ws.rs.Path

@Path("/")
@Api(value = "MediaHub")
@SwaggerDefinition(tags = Array(new Tag(name = "hello", description = "operations useful for debugging")))
trait MovieAPI {
  @Path("principalsForMovieName")
  @ApiOperation(
    value = "...",
    nickname = "",
    httpMethod = "GET",
    consumes = "application/json",
    produces = "text/plain",
    response = classOf[Principal])
  @ApiResponses(Array(
    new ApiResponse(code = 200, message = "OK")))
  def route1: Route = complete("empty")

  @Path("tvSeriesWithGreatestNumberOfEpisodes")
  @ApiOperation(
    value = "...",
    nickname = "",
    httpMethod = "GET",
    consumes = "application/json",
    produces = "text/plain",
    response = classOf[TvSeries])
  @ApiResponses(Array(
    new ApiResponse(code = 200, message = "OK")))
  def route2: Route = complete("empty")



}