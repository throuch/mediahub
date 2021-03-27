package canalplus.mediahub.interfaces.swagger

import akka.http.scaladsl.server.Route
import io.swagger.annotations._
import javax.ws.rs.Path

@Path("/status")
@Api(value = "/status")
@SwaggerDefinition(tags = Array(new Tag(name = "hello", description = "operations useful for debugging")))
trait Status {
  @ApiOperation(
    value = "status",
    tags = Array("status"),
    httpMethod = "GET",
    notes = "This route will return OK if everything goes well")
  @ApiResponses(Array(
    new ApiResponse(code = 200, message = "OK"),
    new ApiResponse(code = 500, message = "There was an internal server error.")
  ))
  def pingSwagger: Option[Route] = None
}
