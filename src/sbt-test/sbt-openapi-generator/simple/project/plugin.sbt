addSbtPlugin("com.github.adenza" % "sbt-openapi-generator" % "0.1.0-SNAPSHOT")
//
//sys.props.get("plugin.version") match {
//  case Some(x) => addSbtPlugin("com.github.adenza" % "sbt-openapi-generator" % x)
//  case _ => sys.error("""|The system property 'plugin.version' is not defined.
//                         |Specify this property using the scriptedLaunchOpts -D.""".stripMargin)
//}