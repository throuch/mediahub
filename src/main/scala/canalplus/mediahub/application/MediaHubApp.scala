package canalplus.mediahub.application

import akka.actor.ActorSystem
import canalplus.mediahub.application.http.GameHttpServer
import canalplus.mediahub.application.injection.GameApplicationMixing
import canalplus.rps.application.actors.ClassicGameActor

object MediaHubApp extends App with GameApplicationMixing {

  implicit val system = ActorSystem("MediaSystem")
  implicit val instance = this

  sys.addShutdownHook(system.terminate())

  val gameActorRef = system.actorOf(ClassicGameActor.props(instance), "GameActor")

  new GameHttpServer(gameActorRef)


}
