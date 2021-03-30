package canalplus.mediahub.application.http.movie

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.StandardRoute
import akka.util.ByteString
import canalplus.mediahub.application.service.MovieService
import thorn.core.application.http.HttpCommon

import scala.concurrent.ExecutionContext

class TopTenEpisodesCountSeries(movieService: MovieService)
                               (implicit val ec: ExecutionContext) extends HttpCommon  {

  val route =
    path("tvSeriesWithGreatestNumberOfEpisodes" ) {
        get {
          complete(
            HttpEntity(
              ContentTypes.`text/plain(UTF-8)`,
              // transform each number to a chunk of bytes
              movieService.tvSeriesWithGreatestNumberOfEpisodes().map(n => ByteString(s"$n\n"))
            )
          )
        }
    }

}

