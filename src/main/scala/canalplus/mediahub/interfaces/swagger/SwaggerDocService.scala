package canalplus.mediahub.interfaces.swagger

import com.github.swagger.akka.SwaggerHttpService
import com.github.swagger.akka.model.{Contact, Info, License}
import com.typesafe.config.ConfigFactory
import canalplus.mediahub.interfaces.swagger.game.MovieAPI

object SwaggerDocService extends SwaggerHttpService {
  val config = ConfigFactory.load()


  override val apiClasses: Set[Class[_]] = Set(
    classOf[Ping],
    classOf[Status],
    classOf[MovieAPI]
  )
  override val host = config.getString("swagger.api.host") //the url of your api, not swagger's json endpoint
  override val basePath = config.getString("swagger.api.base.path")
  override val apiDocsPath = config.getString("swagger.api.doc.path")
  override val info = Info(
    "Swagger Akka http MediaHub",
    "0.1.0",
    "MediaHub",
    termsOfService = "http://swagger.io/terms/",
    license = Some(License("Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0.html")),
    contact = Some(Contact("Thomas ROUCH", "", "thomas.rouch@thorn.consulting"))

  )
}
