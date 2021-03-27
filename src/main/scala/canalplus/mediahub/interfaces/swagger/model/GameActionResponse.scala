package canalplus.mediahub.interfaces.swagger.model

import io.swagger.annotations.ApiModel

@ApiModel(description = "Game action response")
case class GameActionResponse
(
  humanWins: Boolean,
  message: String
)