sys.props.get("plugin.version") match {
  case Some(x) => addSbtPlugin("com.github.adenza" % "sbt-openapi-generator" % x)
  case _ =>
    println("The system property 'plugin.version' is not defined. Fallback to predifined")
    addSbtPlugin("com.github.adenza" % "sbt-openapi-generator" % "0.2-SNAPSHOT")
}