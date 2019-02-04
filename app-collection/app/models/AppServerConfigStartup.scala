package models

import io.ebean.config.dbplatform.DatabasePlatform
import io.ebean.config.dbplatform.IdType
import io.ebean.config.{JsonConfig, ServerConfig}
import io.ebean.event.ServerConfigStartup

class AppServerConfigStartup extends ServerConfigStartup {
  override def onStart(serverConfig: ServerConfig): Unit = {

    serverConfig.setDatabaseSequenceBatchSize(1)
    serverConfig.setDdlGenerate(false)
    serverConfig.setDdlRun(false)
//    serverConfig.setJsonDateTime(JsonConfig.DateTime.ISO8601)

    val dbPlatform = new DatabasePlatform
    dbPlatform.getDbIdentity.setIdType(IdType.IDENTITY)
    dbPlatform.getDbIdentity.setSupportsGetGeneratedKeys(true)
    dbPlatform.getDbIdentity.setSupportsSequence(false)
    dbPlatform.getDbIdentity.setSupportsIdentity(true)
    serverConfig.setDatabasePlatform(dbPlatform)
  }
}
