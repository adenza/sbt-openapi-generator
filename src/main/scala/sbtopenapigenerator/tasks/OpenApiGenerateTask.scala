package sbtopenapigenerator.tasks

import org.openapitools.codegen.{CodegenConstants, DefaultGenerator}
import org.openapitools.codegen.config.{CodegenConfigurator, GlobalSettings}
import sbt.{Def, settingKey, _}
import Keys._
import sbtopenapigenerator.OpenApiGeneratorKeys
import sbtopenapigenerator.configs.OpenApiCodegen

import scala.collection.JavaConverters._
import scala.util.{Failure, Success, Try}

trait OpenApiGenerateTask extends OpenApiCodegen with OpenApiGeneratorKeys {

  protected[this] def openApiGenerateTask: Def.Initialize[Task[Seq[File]]] = Def.task {

    val logger = sbt.Keys.streams.value.log

    //    val conf: String = (OpenApiCodegen / configFile).value
    //    val output: String = (OpenApiCodegen / outputDir).value
    //    val lang: String = (OpenApiCodegen / language).value
    //
    //    if (!lang.equals("")) {
    //      val codegenConfig = if (!conf.equals(""))
    //        Option(CodegenConfigurator.fromFile(conf)).getOrElse(new CodegenConfigurator())
    //      else new CodegenConfigurator()
    //
    //      codegenConfig.setInputSpec((OpenApiCodegen / inputSpec).value)
    //      codegenConfig.setGeneratorName(lang)
    //      if (!output.equals(""))
    //        codegenConfig.setOutputDir(output)
    //
    //      val clientOptInput = codegenConfig.toClientOptInput
    //
    //      val codegen = new DefaultGenerator()
    //      codegen.opts(clientOptInput).generate().asScala
    //    }
    //    else
    //      Seq()

    // New version
    val configurator: CodegenConfigurator = if ((OpenApiCodegen / configFile).value.nonEmpty) {
      CodegenConfigurator.fromFile((OpenApiCodegen / configFile).value)
    } else new CodegenConfigurator()

    if ((OpenApiCodegen / systemProperties).value.nonEmpty) {
      systemProperties.value.foreach { v =>
        configurator.addSystemProperty(v._1, v._2)
      }
    }

    if ((OpenApiCodegen / supportingFilesConstrainedTo).value.nonEmpty) {
      GlobalSettings.setProperty(CodegenConstants.SUPPORTING_FILES, (OpenApiCodegen / supportingFilesConstrainedTo).value.mkString(","))
    } else {
      GlobalSettings.clearProperty(CodegenConstants.SUPPORTING_FILES)
    }

    if ((OpenApiCodegen / modelFilesConstrainedTo).value.nonEmpty) {
      GlobalSettings.setProperty(CodegenConstants.MODELS, (OpenApiCodegen / modelFilesConstrainedTo).value.mkString(","))
    } else {
      GlobalSettings.clearProperty(CodegenConstants.MODELS)
    }

    if ((OpenApiCodegen / apiFilesConstrainedTo).value.nonEmpty) {
      GlobalSettings.setProperty(CodegenConstants.APIS, (OpenApiCodegen / apiFilesConstrainedTo).value.mkString(","))
    } else {
      GlobalSettings.clearProperty(CodegenConstants.APIS)
    }

    if ((OpenApiCodegen / generateApiDocumentation).value) {
      GlobalSettings.setProperty(CodegenConstants.API_DOCS, (OpenApiCodegen / generateApiDocumentation).value.toString)
    }

    if ((OpenApiCodegen / generateModelDocumentation).value) {
      GlobalSettings.setProperty(CodegenConstants.MODEL_DOCS, (OpenApiCodegen / generateModelDocumentation).value.toString)
    }

    if ((OpenApiCodegen / generateModelTests).value) {
      GlobalSettings.setProperty(CodegenConstants.MODEL_TESTS, (OpenApiCodegen / generateModelTests).value.toString)
    }

    if ((OpenApiCodegen / generateApiTests).value) {
      GlobalSettings.setProperty(CodegenConstants.API_TESTS, (OpenApiCodegen / generateApiTests).value.toString)
    }

    if ((OpenApiCodegen / withXml).value) {
      GlobalSettings.setProperty(CodegenConstants.WITH_XML, (OpenApiCodegen / withXml).value.toString)
    }

    // now override with any specified parameters
    (OpenApiCodegen / verbose).map { value =>
      configurator.setVerbose(value)
    }

    (OpenApiCodegen / validateSpec).map { value =>
      configurator.setValidateSpec(value)
    }

    (OpenApiCodegen / skipOverwrite).map { value =>
      configurator.setSkipOverwrite(value)
    }

    (OpenApiCodegen / inputSpec).map { value =>
      configurator.setInputSpec(value)
    }

    (OpenApiCodegen / generatorName).map { value =>
      configurator.setGeneratorName(value)
    }

    (OpenApiCodegen / outputDir).map { value =>
      configurator.setOutputDir(value)
    }

    (OpenApiCodegen / auth).map { value =>
      configurator.setAuth(value)
    }

    (OpenApiCodegen / templateDir).value.map { value =>
      configurator.setTemplateDir(value)
    }

    (OpenApiCodegen / packageName).map { value =>
      configurator.setPackageName(value)
    }

    (OpenApiCodegen / apiPackage).map { value =>
      configurator.setApiPackage(value)
    }

    (OpenApiCodegen / modelPackage).map { value =>
      configurator.setModelPackage(value)
    }

    (OpenApiCodegen / modelNamePrefix).map { value =>
      configurator.setModelNamePrefix(value)
    }

    (OpenApiCodegen / modelNameSuffix).map { value =>
      configurator.setModelNameSuffix(value)
    }

    (OpenApiCodegen / invokerPackage).map { value =>
      configurator.setInvokerPackage(value)
    }

    (OpenApiCodegen / groupId).map { value =>
      configurator.setGroupId(value)
    }

    (OpenApiCodegen / id).map { value =>
      configurator.setArtifactId(value)
    }

    (OpenApiCodegen / version).map { value =>
      configurator.setArtifactVersion(value)
    }

    (OpenApiCodegen / library).value.map { value =>
      configurator.setLibrary(value)
    }

    (OpenApiCodegen / gitHost).value.map { value =>
      configurator.setGitHost(value)
    }

    (OpenApiCodegen / gitUserId).value.map { value =>
      configurator.setGitUserId(value)
    }

    (OpenApiCodegen / gitRepoId).value.map { value =>
      configurator.setGitRepoId(value)
    }

    (OpenApiCodegen / releaseNote).value.map { value =>
      configurator.setReleaseNote(value)
    }

    (OpenApiCodegen / httpUserAgent).value.map { value =>
      configurator.setHttpUserAgent(value)
    }

    (OpenApiCodegen / ignoreFileOverride).value.map { value =>
      configurator.setIgnoreFileOverride(value)
    }

    (OpenApiCodegen / removeOperationIdPrefix).value.map { value =>
      configurator.setRemoveOperationIdPrefix(value)
    }

    (OpenApiCodegen / logToStderr).map { value =>
      configurator.setLogToStderr(value)
    }

    (OpenApiCodegen / enablePostProcessFile).map { value =>
      configurator.setEnablePostProcessFile(value)
    }

    (OpenApiCodegen / skipValidateSpec).map { value =>
      configurator.setValidateSpec(!value)
    }

    (OpenApiCodegen / generateAliasAsModel).map { value =>
      configurator.setGenerateAliasAsModel(value)
    }

    if ((OpenApiCodegen / systemProperties).value.nonEmpty) {
      (OpenApiCodegen / systemProperties).value.foreach { entry =>
        configurator.addSystemProperty(entry._1, entry._2)
      }
    }

    if ((OpenApiCodegen / instantiationTypes).value.nonEmpty) {
      (OpenApiCodegen / instantiationTypes).value.foreach { entry =>
        configurator.addInstantiationType(entry._1, entry._2)
      }
    }

    if ((OpenApiCodegen / importMappings).value.nonEmpty) {
      (OpenApiCodegen / importMappings).value.foreach { entry =>
        configurator.addImportMapping(entry._1, entry._2)
      }
    }

    if ((OpenApiCodegen / typeMappings).value.nonEmpty) {
      (OpenApiCodegen / typeMappings).value.foreach { entry =>
        configurator.addTypeMapping(entry._1, entry._2)
      }
    }

    if ((OpenApiCodegen / additionalProperties).value.nonEmpty) {
      (OpenApiCodegen / additionalProperties).value.foreach { entry =>
        configurator.addAdditionalProperty(entry._1, entry._2)
      }
    }

    if ((OpenApiCodegen / serverVariables).value.nonEmpty) {
      (OpenApiCodegen / serverVariables).value.foreach { entry =>
        configurator.addServerVariable(entry._1, entry._2)
      }
    }

    if ((OpenApiCodegen / languageSpecificPrimitives).value.nonEmpty) {
      (OpenApiCodegen / languageSpecificPrimitives).value.foreach { it =>
        configurator.addLanguageSpecificPrimitive(it)
      }
    }

    if ((OpenApiCodegen / reservedWordsMappings).value.nonEmpty) {
      (OpenApiCodegen / reservedWordsMappings).value.foreach { entry =>
        configurator.addAdditionalReservedWordMapping(entry._1, entry._2)
      }
    }

    val clientOptInput = configurator.toClientOptInput
    val codgenConfig = clientOptInput.getConfig

    if ((OpenApiCodegen / configOptions).value.nonEmpty) {
      val userSpecifiedConfigOptions = (OpenApiCodegen / configOptions).value
      codgenConfig.cliOptions().forEach { it =>
        if (userSpecifiedConfigOptions.contains(it.getOpt)) {
          val opt = it.getOpt
          clientOptInput.getConfig.additionalProperties.replace(opt, userSpecifiedConfigOptions(opt))
        }
      }
    }

    Try {
      val res = new DefaultGenerator().opts(clientOptInput).generate().asScala

      logger.out(s"Successfully generated code to $outputDir")
      res
    } match {
      case Success(value) => value
      case Failure(ex) =>
        throw new Exception("Code generation failed.", ex)
    }
  }
}
