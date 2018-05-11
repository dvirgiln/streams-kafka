name := "spark-consumer"
mainClass in Compile := Some("com.david.ts.consumer.ConsumerMain")

val sparkVersion = "2.3.0"
val sparkDependencies  : Seq[ModuleID] = Seq(
  ("org.apache.spark" %% "spark-core" % sparkVersion % "provided"),
  ("org.apache.spark" %% "spark-streaming" % sparkVersion % "provided"),
  ("org.apache.spark" %% "spark-streaming-kafka-0-10" % sparkVersion).
    exclude("org.apache.spark", "spark-tags_2.11").
    exclude("org.spark-project.spark", "unused")
)
libraryDependencies ++= sparkDependencies
enablePlugins(DockerPlugin)
dockerfile in docker := {
  // The assembly task generates a fat JAR file
  val artifact: File = assembly.value
  val artifactTargetPath = s"/app/${artifact.name}"

  new Dockerfile {
    from("com.david/spark-docker-template:0.2-SNAPSHOT")
    add(artifact, artifactTargetPath)
    env("SPARK_APPLICATION_MAIN_CLASS", "com.david.ts.consumer.ConsumerMain")
    env("SPARK_APPLICATION_JAR_LOCATION", artifactTargetPath)
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
