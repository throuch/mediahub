package canalplus.mediahub.interfaces.swagger

import akka.http.scaladsl.server.Route
import io.swagger.annotations._
import javax.ws.rs.Path

@Path("/ping")
@Api(value = "/ping")
@SwaggerDefinition(tags = Array(new Tag(name = "hello", description = "operations useful for debugging")))
trait Ping {
  @ApiOperation(
    value = "ping",
    tags = Array("ping"),
    httpMethod = "GET",
    notes = "This route will return a output pong")
  @ApiResponses(Array(
    new ApiResponse(code = 200, message = "OK"),
    new ApiResponse(code = 500, message = "There was an internal server error.")
  ))
  def pingSwagger: Option[Route] = None
}
