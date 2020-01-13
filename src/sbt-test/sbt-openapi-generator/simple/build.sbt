import sbt.Keys._
import sbt._

version := "0.1"
scalaVersion := "2.12.4"

enablePlugins(OpenApiGeneratorPlugin)

lazy val generated = Project("generated", file("generated"))
  .settings(
    inConfig(OpenApiCodegen) {
      Seq(
        inputSpec := "openapi.yaml",
        language := "scala-akka",
        configFile := "config1.yaml",
        //outputPath in openApiGenerate := (baseDirectory in ThisBuild).value + "/generated"
      )
    }
  )

lazy val root = (project in file("."))
  .settings(
    name := "openapi-generator-example",
    // Define task dependency
    //  compile := ((compile in Compile) dependsOn (openApiGenerate in OpenApiCodegen)).value,
  )
  .dependsOn(generated)
  .aggregate(generated)


unmanagedSourceDirectories in Compile += baseDirectory.value / "generated"

