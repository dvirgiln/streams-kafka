name := "akka-producer"
mainClass in Compile := Some("com.david.ts.producer.ProducerMain")
enablePlugins(DockerPlugin)
dockerfile in docker := {
  // The assembly task generates a fat JAR file
  val artifact: File = assembly.value
  val artifactTargetPath = s"/app/${artifact.name}"

  new Dockerfile {
    from("openjdk:8-jre")
    add(artifact, artifactTargetPath)
    entryPointShell("java","-Dkafka_endpoint=${BROKER_HOST}:$BROKER_PORT", "-jar", artifactTargetPath)
  }
}
imageNames in docker := Seq(
  // Sets the latest tag
  ImageName(s"com.david/${name.value}:latest"),

  // Sets a name with a tag that contains the project version
  ImageName(
    namespace = Some("com.david"),
    repository = name.value,
    tag = Some(version.value)
  )
)
