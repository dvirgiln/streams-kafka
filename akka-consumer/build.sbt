import com.typesafe.sbt.packager.docker._
name := "akka-consumer"
enablePlugins(JavaAppPackaging)
enablePlugins(DockerPlugin)
enablePlugins(AshScriptPlugin)
dockerBaseImage := "openjdk:jre-alpine"
mainClass in Compile := Some("com.david.ts.consumer.ConsumerMain")