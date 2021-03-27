package canalplus.mediahub.application.injection


import canalplus.core.application.service.MatchService
import canalplus.core.infrastructure.{InMemoryMatchRepository, InMemoryPlayerRepository}
import canalplus.mediahub.application.service.{BasicFeatures, GameLauncher}
import canalplus.mediahub.interfaces.GameService
import canalplus.rps.domain.{AIStrategy, AdvancedGameStrategy, RandomGameStrategy}
import canalplus.rps.infrastructure.InMemoryGameRecorder


trait GameApplicationMixing extends
  GameService with
  MatchService with
  InMemoryMatchRepository with

  BasicFeatures


