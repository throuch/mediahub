package canalplus.mediahub.domain.entities

object MovieService {
  final case class Principal(
                              name: String,
                              birthYear: Int,
                              deathYear: Option[Int],
                              profession: List[String])
  final case class TvSeries(
                             original: String,
                             startYear: Int,
                             endYear: Option[Int],
                             genres: List[String])
}