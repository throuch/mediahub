package canalplus.mediahub.application.injection

import canalplus.mediahub.application.service.impl.MovieServiceImpl
import canalplus.mediahub.domain.repositories.{TitlePrincipalsRepositories, TvSeriesRepositories}


trait Module extends MovieServiceImpl with TitlePrincipalsRepositories with TvSeriesRepositories



