package canalplus.mediahub.interfaces



trait MovieWebService {
  def principalsForMovieName(name: String): Seq[Principal]

  def tvSeriesWithGreatestNumberOfEpisodes(): Seq[TvSeries]
}
