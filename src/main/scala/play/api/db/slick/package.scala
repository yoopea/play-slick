package play.api.db

import play.api.mvc.{ Request, AnyContent }
import play.api.Application
import scala.slick.session.Database
import javax.sql.{ DataSource => SqlDataSource }
import play.api.mvc.Flash

package object slick {
  
  //DB helpers that mimics play.api.db.DB
  def DB(implicit app: Application): Database = DB(Config.defaultName)(app)
  
  def DB(name: String)(implicit app: Application): Database = {
    Config.database(name)
  }
  
  //for export to user app
  type Session = scala.slick.session.Session
  
  type DBSessionInvoke[A] = DBSessionRequest[A] => Request[A]

  //implicit / automatic transforms of DBSessionRequest to Request and Session
  implicit def dbSessionRequestAsRequest[A](implicit r: DBSessionRequest[A]): Request[A] = r.request
  implicit def dbSessionRequestAsSession[A](implicit r: DBSessionRequest[A]): Session = r.slickSession
}
