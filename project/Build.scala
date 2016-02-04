import sbt._
import Keys._
import org.scalatra.sbt._
import com.earldouglas.xwp.JettyPlugin

object TodoListBuild extends Build {
  val ScalatraVersion = "2.4.0.RC1"

  lazy val project = Project (
    "todo-list",
    file("."),
    settings = ScalatraPlugin.scalatraSettings ++ Seq(
      libraryDependencies ++= Seq(
        "javax.servlet" % "javax.servlet-api" % "3.1.0" % "provided",
        "org.scalatra" %% "scalatra" % ScalatraVersion,
        "ch.qos.logback" % "logback-classic" % "1.1.3" % "runtime",
        "org.eclipse.jetty" % "jetty-webapp" % "9.2.14.v20151106" % "container",
        "org.scalatra" %% "scalatra-json" % ScalatraVersion,
        "org.json4s"   %% "json4s-jackson" % "3.3.0.RC1",
        "org.scalatra" %% "scalatra-scalatest" % ScalatraVersion % "test"
      )
    )
  ).enablePlugins(JettyPlugin)
}
