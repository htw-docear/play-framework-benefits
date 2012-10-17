import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "play-framework-benefits"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      "info.schleichardt" %% "play-2-basic-auth" % "0.1-SNAPSHOT"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
      resolvers += "schleichardts Github" at "http://schleichardt.github.com/jvmrepo/"
    )

}
