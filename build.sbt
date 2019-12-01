lazy val `sbt-openapi-generator` = project in file(".")

//import sbt.Keys._
//import sbt._

scalaVersion := "2.12.4"
organization := "com.github.adenza"
name := "sbt-openapi-generator"
//licenses := Seq(("", url("")))
description := "An sbt plugin that offers openapi generation features"
startYear := Some(2019)
homepage := scmInfo.value map (_.browseUrl)
scmInfo := Some(ScmInfo(url("https://github.com/adenza/sbt-openapi-generator/"), "scm:git:git@github.com:adenza/sbt-openapi-generator.git"))

crossSbtVersions := List("0.13.17", "1.1.5")


libraryDependencies ++= Seq()


enablePlugins(SbtPlugin)

scriptedLaunchOpts += ("-Dplugin.version=" + version.value)
scriptedLaunchOpts ++= sys.process.javaVmArguments.filter(
  a => Seq("-Xmx", "-Xms", "-XX", "-      Dsbt.log.noformat").exists(a.startsWith)
)
scriptedBufferLog := false
