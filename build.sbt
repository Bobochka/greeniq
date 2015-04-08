name := """greeniq"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  jdbc,
  "postgresql" % "postgresql" % "9.1-901.jdbc4",
  "org.webjars" % "bootstrap" % "3.3.4",
  anorm,
  cache,
  ws
)