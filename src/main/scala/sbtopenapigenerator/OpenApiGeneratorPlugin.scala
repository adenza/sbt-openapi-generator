package sbtopenapigenerator

import sbt.{Def, _}
import Keys._
import sbt.plugins.JvmPlugin
import sbt.librarymanagement.Configuration
import sbtopenapigenerator.configs.OpenApiCodegen
import sbtopenapigenerator.tasks.{OpenApiGenerateTask, OpenApiGeneratorsTask}

object OpenApiGeneratorPlugin extends AutoPlugin
  with OpenApiGeneratorsTask
  with OpenApiGenerateTask
  with OpenApiCodegen {
  self =>

  override def requires: JvmPlugin.type = sbt.plugins.JvmPlugin

  override def trigger: PluginTrigger = allRequirements

  object autoImport extends OpenApiGeneratorKeys {
    val OpenApiCodegen = self.OpenApiCodegen
  }

 // import autoImport._

  override def globalSettings: Seq[Def.Setting[String]] = Seq(
    outputDir := s"${baseDirectory.in(ThisBuild).value}/generated"
  )

  override lazy val projectSettings: Seq[Def.Setting[_]] = inConfig(OpenApiCodegen)(Seq[Setting[_]](
    openApiGenerate := openApiGenerateTask.value,
    openApiGenerators := openApiGeneratorsTask.value,
    configFile := "",
    inputSpec := "swagger.yaml",
    language := "",
    additionalProperties := Map.empty[String, String]
  ))

  override def projectConfigurations: List[Configuration] = OpenApiCodegen :: Nil

}
