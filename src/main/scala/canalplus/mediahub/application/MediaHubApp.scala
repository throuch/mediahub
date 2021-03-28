package canalplus.mediahub.application

import akka.actor.ActorSystem
import canalplus.mediahub.application.http.HttpServer
import canalplus.mediahub.application.injection.Module

object MediaHubApp extends App with Module {

  implicit val system = ActorSystem("MediaSystem")
  implicit val instance = this

  sys.addShutdownHook(system.terminate())

  //val gameActorRef = system.actorOf(ClassicGameActor.props(instance), "GameActor")

  new HttpServer


}
