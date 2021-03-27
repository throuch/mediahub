
import sbt.Keys._
import sbt._

object Utils {

  def generateDockerTag(buildversion: String): String =
    buildversion.substring(0, buildversion.indexOf('-'))


}