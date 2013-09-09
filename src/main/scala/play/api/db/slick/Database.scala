package play.api.db.slick

import scala.slick.session.PlayDatabase
import play.api.Application

object Database {
  //We do not want to Database to be a case class since equal dbProviders doesn't mean equal databases
  def apply(dbProvider: DBSourceProvider) = {
    new Database(dbProvider)
  }
  
  def apply(name: String)(implicit app: Application) = {
    new Database(Config.loadDBSourceProvider(name))
  }
}

class Database(dbProvider: DBSourceProvider) extends PlayDatabase {
  override val datasource = dbProvider.datasource
}