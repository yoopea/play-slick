package play.api.db.slick

import scala.slick.session.PlayDatabase
import javax.sql.{ DataSource => SqlDataSource }
import scala.slick.driver._

class DBSourceProvider(val datasource: SqlDataSource, val name: String)

object Database {
  
  //we do not want to use case class on Database since equal dbProviders doesn't mean equal databases, but we want the apply method
  def apply(dbProvider: DBSourceProvider) = {
    new Database(dbProvider)
  }
}

class Database(dbProvider: DBSourceProvider) extends PlayDatabase {
  override val datasource = dbProvider.datasource
}