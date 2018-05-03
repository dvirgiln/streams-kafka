resolvers += Resolver.sonatypeRepo("releases")

addSbtPlugin("com.lucidchart" % "sbt-cross" % "1.0")

addSbtPlugin("org.scalariform" % "sbt-scalariform" % "1.6.0")

addSbtPlugin("io.spray" % "sbt-revolver" % "0.9.1")
addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.3")
addSbtPlugin("se.marcuslonnberg" % "sbt-docker" % "1.5.0")
addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.8.0")
