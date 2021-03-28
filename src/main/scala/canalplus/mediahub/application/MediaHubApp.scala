package canalplus.mediahub.application

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, Materializer}
import canalplus.mediahub.application.http.HttpServer
import canalplus.mediahub.application.injection.Module

object MediaHubApp extends App with Module {

  implicit val system = ActorSystem("MediaSystem")

  implicit val mat: Materializer = ActorMaterializer()
  implicit val instance = this

  sys.addShutdownHook(system.terminate())

  new HttpServer


}
