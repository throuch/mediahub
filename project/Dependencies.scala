import sbt._


object Dependencies {

  lazy val commonDependencies = Seq(
    "org.scalactic" %% "scalactic" % "3.0.5" % Test,
    "org.scalatest" %% "scalatest" % "3.0.5" % Test,
    // "org.json4s" %% "json4s-jackson" % "3.6.7",
    "ch.qos.logback" % "logback-classic" % "1.2.3",
    "ch.qos.logback" % "logback-core" % "1.2.3"

  )

  lazy val akkaArtifacts = new {
    val akka_version = "2.5.23"
    val akka_http_version = "10.1.10"

    val akka_actor = "com.typesafe.akka" %% "akka-actor" % akka_version
    val akka_slf4j = "com.typesafe.akka" %% "akka-slf4j" % akka_version
    val akka_remote = "com.typesafe.akka" %% "akka-remote" % akka_version
    val akka_http = "com.typesafe.akka" %% "akka-http" % akka_http_version
    val akka_cluster = "com.typesafe.akka" %% "akka-cluster" % akka_version
    val akka_testkit = "com.typesafe.akka" %% "akka-testkit" % akka_version
    val akka_http_testkit = "com.typesafe.akka" %% "akka-http-testkit" % akka_http_version
    val akka_http_spray_json = "com.typesafe.akka" %% "akka-http-spray-json" % akka_http_version


  }

  lazy val swaggerDependencies = Seq(
    "com.github.swagger-akka-http" %% "swagger-akka-http" % "1.1.0",
    "co.pragmati" %% "swagger-ui-akka-http" % "1.2.0",
    "ch.megard" %% "akka-http-cors" % "0.4.1",
    "io.swagger.core.v3" % "swagger-core" % "2.0.9",
    "io.swagger.core.v3" % "swagger-annotations" % "2.0.9",
    "io.swagger.core.v3" % "swagger-models" % "2.0.9",
    "io.swagger.core.v3" % "swagger-jaxrs2" % "2.0.9"
  )
  lazy val akkaDependencies = Seq(
    akkaArtifacts.akka_actor,
    akkaArtifacts.akka_slf4j,
    akkaArtifacts.akka_remote,
    akkaArtifacts.akka_cluster,
    akkaArtifacts.akka_http,
    akkaArtifacts.akka_http_spray_json,
    akkaArtifacts.akka_testkit % Test,
    akkaArtifacts.akka_http_testkit % Test
  )

}


