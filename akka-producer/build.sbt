name := "akka-producer"
enablePlugins(JavaAppPackaging)
enablePlugins(DockerPlugin)
enablePlugins(AshScriptPlugin)
dockerBaseImage := "openjdk:jre-alpine"
mainClass in Compile := Some("com.david.ts.producer.ProducerMain")
