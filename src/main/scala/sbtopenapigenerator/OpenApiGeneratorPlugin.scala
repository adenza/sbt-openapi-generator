package sbtopenapigenerator

import sbt.{Def, _}
import Keys._
import org.openapitools.codegen.DefaultGenerator
import org.openapitools.codegen.config.CodegenConfigurator
import sbt.plugins.JvmPlugin
import sbt.librarymanagement.Configuration

object OpenApiGeneratorPlugin extends AutoPlugin {
  self =>

  protected lazy val OpenApiCodegen: Configuration = Configuration.of(
    id = "OpenApiCodegen",
    name = "OpenApi Generator codegen plugin configuration"
  )

  override def requires: JvmPlugin.type = sbt.plugins.JvmPlugin

  override def trigger: PluginTrigger = allRequirements

  object autoImport extends OpenApiGeneratorKeys {
    val OpenApiCodegen = self.OpenApiCodegen
  }

  import autoImport._

  override def globalSettings: Seq[Def.Setting[String]] = Seq(
    outputPath := s"${baseDirectory.in(ThisBuild).value}/generated"
  )

  override lazy val projectSettings: Seq[Def.Setting[_]] = inConfig(OpenApiCodegen)(Seq[Setting[_]](
    openApiGenerate := codegenTask.value,
    configFile := "",
    inputSpec := "swagger.yaml",
    language := "",
    additionalProperties := Map.empty[String, String]
  ))

  override def projectConfigurations: List[Configuration] = OpenApiCodegen :: Nil

  private[this] def codegenTask: Def.Initialize[Task[Seq[File]]] = Def.task {
    import scala.collection.JavaConverters._

    val logger = sbt.Keys.streams.value.log

    val conf: String = (OpenApiCodegen / configFile).value
    val output: String = (OpenApiCodegen / outputPath).value
    val lang: String = (OpenApiCodegen / language).value

    if (!lang.equals("")) {
      val codegenConfig = if (!conf.equals(""))
        Option(CodegenConfigurator.fromFile(conf)).getOrElse(new CodegenConfigurator())
      else new CodegenConfigurator()

      codegenConfig.setInputSpec((OpenApiCodegen / inputSpec).value)
      codegenConfig.setGeneratorName(lang)
      if (!output.equals(""))
        codegenConfig.setOutputDir(output)

      val clientOptInput = codegenConfig.toClientOptInput

      val codegen = new DefaultGenerator()
      codegen.opts(clientOptInput).generate().asScala
    }
    else
      Seq()
  }
}
