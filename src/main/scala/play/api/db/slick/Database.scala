package play.api.db.slick

import scala.slick.session.PlayDatabase

object Database {
  //We do not want to Database to be a case class since equal dbProviders doesn't mean equal databases
  def apply(dbProvider: DBSourceProvider) = {
    new Database(dbProvider)
  }
}

class Database(dbProvider: DBSourceProvider) extends PlayDatabase {
  override val datasource = dbProvider.datasource
}