package play.api.db.slick

import scala.concurrent.Future
import scala.slick.session.PlayDatabase

import play.api.Application
import play.api.mvc._
import play.api.mvc.Action
import play.api.mvc.ActionBuilder
import play.api.mvc.AnyContent
import play.api.mvc.Request
import play.api.mvc.Results
import play.api.mvc.SimpleResult
import play.api.mvc.WrappedRequest

object DBAction extends WithDB(Config.defaultDatabase(play.api.Play.current)) { //<-- Play.current
  override def invokeBlock[A](request: Request[A], block: DBSessionRequest[A] => Future[SimpleResult]): Future[SimpleResult] = {
    slickDatabase.withSession { session =>
      block(DBSessionRequest(session, request))
    }
  }
  
  def using(database: PlayDatabase) = new WithDB(database)
  
  def using(sourceProvider: DBSourceProvider) = new WithDB(Database(sourceProvider))
  
  def forName(name: String, app: Application = play.api.Play.current) = new WithDB(Config.database(name)(app))
}