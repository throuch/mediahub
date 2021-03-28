package canalplus.mediahub.application.actors

import akka.actor.{Actor, ActorLogging, Props}
import akka.stream.scaladsl.Sink
import canalplus.mediahub.application.service.MovieService
import canalplus.mediahub.domain.entities.MovieService.{Principal, TvSeries}


/**
 *
 *
 */
class MediaHubActor(movieService: MovieService) extends Actor with ActorLogging {
  import MediaHubActor._

  override def receive: Receive = {
    case GetPrincipals(movieName) =>
      sender() ! process(movieName)
    case GetTopTenSeriesEpisodesCount =>
      sender() ! process2()
    case msg ⇒ log.warning(s"DEBUG: unrecognized message $msg")
  }

  def process(movieName: String): Seq[Principal] = {
    movieService.principalsForMovieNameStream(movieName)...

    runWith(Sink.Seq)
  }

  def process2(): Seq[TvSeries] = {
    ???
  }
}

object MediaHubActor {
  def props(appContext: MovieService): Props =
    Props(new MediaHubActor(appContext))

  case class GetPrincipals(movieName: String)

  case object GetTopTenSeriesEpisodesCount
}
