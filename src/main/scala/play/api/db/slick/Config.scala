package play.api.db.slick

import scala.slick.driver._
import play.api.Configuration
import play.api.Application

object Config {
  lazy val defaultName = "default"

  private def datasource[A](name: String, app: Application) = {
    val conf = app.configuration
    conf.getConfig(s"db.$name") match {
      case None => throw conf.reportError(s"db.$name", s"While loading datasource: could not find db.$name in configuration")
      case _ => play.api.db.DB.getDataSource(name)(app)
    }
  }

  private def driverByName: Map[String, ExtendedDriver] = Map(
    "org.apache.derby.jdbc.EmbeddedDriver" -> DerbyDriver,
    "org.h2.Driver" -> H2Driver,
    "org.hsqldb.jdbcDriver" -> HsqldbDriver,
    "com.mysql.jdbc.Driver" -> MySQLDriver,
    "org.postgresql.Driver" -> PostgresDriver,
    "org.sqlite.JDBC" -> SQLiteDriver,
    "com.microsoft.sqlserver.jdbc.SQLServerDriver" -> SQLServerDriver,
    "net.sourceforge.jtds.jdbc.Driver" -> SQLServerDriver)

  lazy val driver: ExtendedDriver = driver(defaultName)(play.api.Play.current)

  def driver(name: String)(implicit app: Application): ExtendedDriver = {
    val conf = app.configuration
    val key = s"db.$name.driver"
    conf.getString(key).map { driverName =>
      driverByName.get(driverName).getOrElse {
        throw conf.reportError(
          key, s"Slick error : Unknown jdbc driver found in application.conf: [$driverName]")
      }
    }.getOrElse {
      throw conf.reportError(
        key, s"Slick error : jdbc driver not defined in application.conf for db.$name.driver key")
    }
  }

  def loadDBSourceProvider(name: String)(implicit app: Application) = {
    new DBSourceProvider(datasource(name, app), name)
  }

  def database(name: String)(implicit app: Application) = {
    Database(loadDBSourceProvider(name))
  }

  def defaultDatabase(implicit app: Application) = {
    database(defaultName)
  }
}
