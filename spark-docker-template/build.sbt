name := "spark-docker-template"

enablePlugins(DockerPlugin)
dockerfile in docker := {

  new Dockerfile {
    from("bde2020/spark-submit:2.3.0-hadoop2.7")
    add(new File(baseDirectory.value.getAbsolutePath +"/template.sh"),"/")
    //run("apt-get-install apt-transport-https")
    //run("echo \"deb https://dl.bintray.com/sbt/debian /\" > /etc/apt/sources.list.d/sbt.list",
      //"apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 2EE0EA64E40A89B84B2DF73499E82A75642AC823")
    workDir("/app")

    cmd("/template.sh")
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

