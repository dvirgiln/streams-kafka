name := "ts-akka-streams"

version := "0.1"
val akkaVersion = "2.5.4"
val akkaHttpVersion = "10.0.10"
val circeVersion = "0.8.0"
val commonDependencies: Seq[ModuleID] = Seq(
  "org.scalatest" %% "scalatest" % "3.0.1",
  "org.slf4j" % "slf4j-log4j12" % "1.7.10"
)
scalaVersion := "2.12.4"

lazy val commonSettings = Seq(
  organization := "ean.pulse",
  scalaVersion := "2.12.4"
)

val log4j : Seq[ModuleID] = Seq("log4j" % "log4j" % "1.2.17")

val circe : Seq[ModuleID] = Seq(
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,
  "io.circe" %% "circe-java8" % circeVersion,
  "io.circe" %% "circe-java8" % circeVersion,
  "io.circe" %% "circe-optics" % circeVersion,
  "org.scalaz" %% "scalaz-core" % "7.2.14" // for circe optics
)

val akkaDependencies  : Seq[ModuleID] = commonDependencies ++ Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion % Test,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test,
  "com.typesafe.akka" %% "akka-stream-kafka" % "0.20"

)

lazy val akkaProducer = project.in(file("akka-producer")).
  settings(commonSettings).
  settings(libraryDependencies ++= akkaDependencies ++ circe)

lazy val akkaConsumer = project.in(file("akka-consumer")).
  settings(commonSettings).
  settings(libraryDependencies ++= akkaDependencies ++ circe)


lazy val root = (project in file(".")).
  aggregate(akkaProducer, akkaConsumer)
