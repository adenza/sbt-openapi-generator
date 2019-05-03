import sbt.Keys._
import sbt._

version := "0.1"
scalaVersion := "2.12.4"

// Define spec file for all projects
inputFile in openApiGenerate := "spec.yaml"

lazy val root = (project in file("."))
  .settings(
    name := "openapi-generator-example",
    // Define task dependency
    compile := ((compile in Compile) dependsOn openApiGenerate).value,
    // Define plugin settings
    language in openApiGenerate := "scala",
    outputPath in openApiGenerate := s"${baseDirectory.value}/generated"
  )
