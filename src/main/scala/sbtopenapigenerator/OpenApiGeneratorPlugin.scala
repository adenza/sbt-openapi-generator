package sbtopenapigenerator

import sbt._
import Keys._

object OpenApiGeneratorPlugin extends AutoPlugin {

  override def requires = sbt.plugins.JvmPlugin

  override def trigger: PluginTrigger = allRequirements

  object autoImport {
    val openApiGenerate = taskKey[Unit]("openApi code generator")
    val language = settingKey[String]("Generated language")
    val inputFile = settingKey[String]("Spec File")
    val outputPath = settingKey[String]("Output path")
    val versionApi = settingKey[String]("Api version")
  }

  import autoImport._

  override def globalSettings = Seq(
    outputPath := s"${baseDirectory.in(ThisBuild).value}/generated",
    versionApi := "3"
  )

  override lazy val projectSettings = Seq(
    libraryDependencies += "org.openapitools" % "openapi-generator-cli" % "4.2.1" % Runtime,
    //libraryDependencies += "io.swagger.codegen.v3" % "swagger-codegen-cli" % "3.0.8" % Runtime,
    openApiGenerate := Def.taskDyn {
      val log = sbt.Keys.streams.value.log

      val output: String = (openApiGenerate / outputPath).value
      val lang: String = (openApiGenerate / language).value
      val ver: String = (openApiGenerate / versionApi).value
      val spec: String = (openApiGenerate / inputFile).value

      Def.task {
        log.info(s"LANG = $lang")
        (runMain in Runtime)
         // .toTask(" io.swagger.codegen.v3.cli.SwaggerCodegen "
          .toTask(" org.openapitools.codegen.OpenAPIGenerator "
            + " generate "
            + " -o " + output
            + " -i " + spec
          //  + " -l " + lang
            + " -g " + lang
         //   + " -v " + ver
          ).value
      }
    }.value
  )
}
