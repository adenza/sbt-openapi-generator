# sbt-openapi-generator [ ![Download](https://api.bintray.com/packages/adenza/sbt-plugins/sbt-openapi-generator/images/download.svg) ](https://bintray.com/adenza/sbt-plugins/sbt-openapi-generator/_latestVersion) ![Scala CI](https://github.com/adenza/sbt-openapi-generator/workflows/Scala%20CI/badge.svg?branch=master)

This plugin use [OpenApi-Generator](https://github.com/OpenAPITools/openapi-generator)(ex Swagger)
to generate Models and Controllers for REST services

# Installation

Step 1: create `project/sbt-openapi-generator.sbt` with:

```sbt
resolvers += Resolver.bintrayIvyRepo("adenza", "sbt-plugins")
addSbtPlugin("com.github.adenza" % "sbt-openapi-generator" % "0.2.0")
```

# Configuration

Most configuration settings is similar to [CLI commands](https://github.com/OpenAPITools/openapi-generator#3---usage) 

Base configuration is based on project module as recommended way to separate generations by modules.

With the next example module `generated` will be defined 

```sbt
lazy val generated = project.in(file("generated"))
  .settings(
    inConfig(OpenApiCodegen) {
      Seq(
        inputSpec := "openapi.yaml",
        configFile := "config.yaml",
        // outputPath := (baseDirectory in ThisBuild).value + "/generated"
      )
    }
  )
```

Settings will be picked up from `configFile` first if defined and then overwritten with module specified settings

There is an auto helpers to redifine boolean settings to make settings more readable:
```sbt
        validateSpec := SettingDisabled,
        generateModelTests := SettingEnabled,
```

# Execution 

To print list of available languages use 
```shell script
sbt openApiGenerators
```

To run generation process
```shell script
sbt openApiGenerate
```
or per module
```shell script
sbt generated/openApiGenerate
```
# Contribution and Tests

Write plugin integration tests under [/src/sbt-test](src/sbt-test)

To run tests execute
```shell script
sbt scripted
```
