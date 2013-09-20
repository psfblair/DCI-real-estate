package net.phobot.realestate

import com.mysema.query.scala.sql.ScalaMetaDataSerializer
import com.mysema.query.scala.ScalaTypeMappings
import com.mysema.query.sql.codegen.{MetaDataExporter, DefaultNamingStrategy}
import java.sql.DriverManager

object DatabaseCodeGenerator extends scala.App {
  Class.forName("org.h2.Driver").newInstance()
  val connection = DriverManager.getConnection ("jdbc:h2:~/Documents/workspace-IDEA/Scala-RealEstateClosing/test")

  val directory = new java.io.File("src/main/scala")
  val namingStrategy = new DefaultNamingStrategy()
  val exporter = new MetaDataExporter()
  exporter.setNamePrefix("Q")
  exporter.setPackageName("net.phobot.realestate.model")
  exporter.setSchemaPattern("PUBLIC")
  exporter.setTargetFolder(directory)
  exporter.setSerializerClass(classOf[ScalaMetaDataSerializer])
  exporter.setCreateScalaSources(true)
  exporter.setTypeMappings(ScalaTypeMappings.create)
  exporter.export(connection.getMetaData)
}
