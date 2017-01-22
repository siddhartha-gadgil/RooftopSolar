enablePlugins(ScalaJSPlugin)

lazy val scalaV = "2.11.8"

lazy val root = (project in file(".")).
  settings(
    organization := "atree.org",
    name := "Rooftop-Solar",
    version := "0.5",
    scalaVersion := scalaV,
    libraryDependencies ++=
      Seq(
        "com.lihaoyi" %%% "scalatags" % "0.6.1",
        "org.scala-js" %%% "scalajs-dom" % "0.9.0")
  )

initialCommands in console := "import solar._; import SolarData._"
