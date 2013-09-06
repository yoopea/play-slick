package play.api.db.slick

import scala.concurrent.Future
import scala.slick.session.PlayDatabase

import play.api.mvc.ActionBuilder
import play.api.mvc.Request
import play.api.mvc.SimpleResult

class WithDB(val slickDatabase: PlayDatabase) extends ActionBuilder[DBSessionRequest] {
  override def invokeBlock[A](request: Request[A], block: DBSessionRequest[A] => Future[SimpleResult]): Future[SimpleResult] = {
    slickDatabase.withSession { session =>
      block(DBSessionRequest(session, request))
    }
  }
}