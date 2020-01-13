lazy val `sbt-openapi-generator` = Project("sbt-openapi-generator", file("."))

scalaVersion := "2.12.4"
organization := "com.github.adenza"
name := "sbt-openapi-generator"
description := "An sbt plugin that offers openapi generation features"
startYear := Some(2019)
homepage := scmInfo.value map (_.browseUrl)
scmInfo := Some(ScmInfo(url("https://github.com/adenza/sbt-openapi-generator/"), "scm:git:git@github.com:adenza/sbt-openapi-generator.git"))

crossSbtVersions := List("0.13.17", "1.1.5")

libraryDependencies += "org.openapitools" % "openapi-generator" % "4.2.2"

enablePlugins(SbtPlugin)

scriptedLaunchOpts := {
  scriptedLaunchOpts.value ++ Seq("-Xmx1024M", "-Dplugin.version=" + version.value)
}
scriptedBufferLog := false
