name := "streams-kafka"
organization := "com.david"
version := "0.1"
val akkaVersion = "2.5.4"
val circeVersion = "0.8.0"

val commonDependencies: Seq[ModuleID] = Seq(
  "org.scalatest" %% "scalatest" % "3.0.1",
  "org.slf4j" % "slf4j-log4j12" % "1.7.10"
)
scalaVersion := "2.12.2"

lazy val commonSettings = Seq(
  organization := "com.david",
  scalaVersion := "2.12.2"
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




val meta = """META.INF(.)*""".r
val assemblySettings=Seq(assemblyMergeStrategy in assembly := {
  case PathList("javax", "servlet", xs@_*) => MergeStrategy.first
  case PathList(ps@_*) if ps.last endsWith ".html" => MergeStrategy.first
  case n if n.startsWith("reference.conf") => MergeStrategy.concat
  case n if n.endsWith(".conf") => MergeStrategy.concat
  case meta(_) => MergeStrategy.discard
  case x => MergeStrategy.first

})


lazy val akkaProducer = project.in(file("akka-producer")).
  settings(commonSettings).
  settings(libraryDependencies ++= akkaDependencies ++ circe)

lazy val akkaConsumer = project.in(file("akka-consumer")).
    settings(commonSettings).
    settings(libraryDependencies ++= akkaDependencies ++ circe)

lazy val sparkConsumer = project.in(file("spark-consumer")).
  settings(libraryDependencies ++= circe ++ commonDependencies).
  settings(Seq(organization := "com.david", scalaVersion := "2.11.8"))
lazy val root = (project in file(".")).
  aggregate(akkaProducer, akkaConsumer, sparkConsumer)
