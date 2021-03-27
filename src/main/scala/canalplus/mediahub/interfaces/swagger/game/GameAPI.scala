package canalplus.mediahub.interfaces.swagger.game

import akka.http.scaladsl.server.Route
import io.swagger.annotations._
import akka.http.scaladsl.server.Directives.complete
import javax.ws.rs.Path
import canalplus.mediahub.interfaces.swagger.model.{GameAction, GameActionResponse}

@Path("/")
@Api(value = "GameAction")
@SwaggerDefinition(tags = Array(new Tag(name = "hello", description = "operations useful for debugging")))
trait GameAPI {
  @Path("play")
  @ApiOperation(
    value = "Play Rock or Paper or Scissors",
    nickname = "play",
    httpMethod = "POST",
    consumes = "application/json",
    produces = "text/plain",
    response = classOf[GameActionResponse])
  @ApiImplicitParams(
    Array(
      new ApiImplicitParam(
        name = "body",
        value = "Game action",
        dataTypeClass = classOf[GameAction],
        required = true,
        paramType = "body"
      )
    )
  )
  @ApiResponses(Array(
    new ApiResponse(code = 200, message = """You played <play>, I played <AI-play>, you <win/lose>"""),
    new ApiResponse(code = 418,
      message = """You played <play>, I played <AI-play>, you <win/lose>"""),
    new ApiResponse(code = 403, message = "Invalid input, play [rock|paper|scissors]]")))
  def play: Route = complete("empty")

  @Path("reset")
  @ApiOperation(
    value = "Reset results",
    nickname = "reset",
    httpMethod = "GET")
  @ApiResponses(Array(
    new ApiResponse(code = 200, message = "OK"),
  ))
  def reset: Route = complete("empty")

  @Path("results")
  @ApiOperation(
    value = "Results",
    nickname = "results",
    httpMethod = "GET",
    produces = "application/json")
  @ApiResponses(Array(
    new ApiResponse(code = 200, message = "{\"player\": <number of win>, \"computer\": <number of win>}"),
  ))
  def results: Route = complete("empty")

}
