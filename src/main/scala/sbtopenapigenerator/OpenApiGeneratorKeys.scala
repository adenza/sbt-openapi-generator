package sbtopenapigenerator

import sbt._

trait OpenApiGeneratorKeys {
  final val openApiGenerate = taskKey[Seq[File]]("openApi code generator")
  final val language = settingKey[String]("Generated language")
  final val inputSpec = settingKey[String]("Spec File")
  final val outputPath = settingKey[String]("Output path")
  final val configFile = settingKey[String]("OpenApi Configuration File")
  final val additionalProperties = settingKey[Map[String,String]]("Additional properties")
}
