name := """foo app"""

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.6" // or "2.10.4"

libraryDependencies ++= Seq(
  "org.webjars" %% "webjars-play" % "2.4.0",
  "com.typesafe.slick" %% "slick" % "3.0.0",
  "com.typesafe.play" %% "play-slick" % "1.0.0",
  "com.typesafe.play" %% "play-slick-evolutions" % "1.0.0"
)

fork in Test := false

lazy val root = (project in file(".")).enablePlugins(PlayScala)