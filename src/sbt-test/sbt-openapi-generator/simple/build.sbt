scalaVersion := "2.12.4"

enablePlugins(OpenApiGeneratorPlugin)

lazy val generated = Project("generated", file("generated"))
  .settings(
    inConfig(OpenApiCodegen) {
      Seq(
        inputSpec := "openapi.yaml",
        language := "scala-akka",
        configFile := "config.yaml",
        //outputPath in openApiGenerate := (baseDirectory in ThisBuild).value + "/generated"
      )
    }
  )

lazy val root = (project in file("."))
  .settings(
    name := "openapi-generator-example",
  )
  .dependsOn(generated)
  .aggregate(generated)


unmanagedSourceDirectories in Compile += baseDirectory.value / "generated"

