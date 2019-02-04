name := """collection-app"""
organization := "ru.tsk"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala, PlayEbean)

playEbeanModels in Compile := Seq("models.*")
//playEbeanDebugLevel := 4
playEbeanAgentArgs += ("detect" -> "false")

inConfig(Test)(PlayEbean.scopedSettings)

playEbeanModels in Test := Seq("models.*")

scalaVersion := "2.12.6"

crossScalaVersions := Seq("2.12.6", "2.11.12")

libraryDependencies += guice
libraryDependencies += jdbc
libraryDependencies += evolutions
libraryDependencies += "net.codingwell" %% "scala-guice" % "4.2.1"
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
libraryDependencies += "org.apache.derby" % "derby" % "10.14.2.0"
libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.0"
libraryDependencies += "io.ebean" % "ebean" % "11.32.1"
libraryDependencies += "io.ebean" % "ebean-agent" % "11.27.1"
libraryDependencies += "com.google.guava" % "guava" % "27.0.1-jre"
libraryDependencies += "com.fasterxml.jackson.core" % "jackson-core" % "2.9.8"
libraryDependencies += "com.fasterxml.jackson.core" % "jackson-databind" % "2.9.8"
libraryDependencies += "com.fasterxml.jackson.core" % "jackson-annotations" % "2.9.8"
libraryDependencies += "com.fasterxml.jackson.datatype" % "jackson-datatype-jsr310" % "2.9.8"
libraryDependencies += "com.fasterxml.jackson.datatype" % "jackson-datatype-jdk8" % "2.9.8"
