import sbt.Keys._
import sbt._

version := "0.1"
scalaVersion := "2.12.4"

lazy val root = (project in file("."))
  .settings(
    name := "openapi-generator-example",
    // Define task dependency
    compile := ((compile in Compile) dependsOn openApiGenerate).value,
  )

unmanagedSourceDirectories in Compile += baseDirectory.value / "generated"

// Define spec file for all projects
inputFile in openApiGenerate := "openapi.yaml"
language in openApiGenerate := "scala-akka" //-akka"
//outputPath in openApiGenerate := (baseDirectory in ThisBuild).value + "/generated"

