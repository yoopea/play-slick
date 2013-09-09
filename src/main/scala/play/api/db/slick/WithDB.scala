package play.api.db.slick

import scala.concurrent.Future
import play.api.mvc.ActionBuilder
import play.api.mvc.Request
import play.api.mvc.SimpleResult
import scala.concurrent.ExecutionContext

class WithDB(val slickDatabase: Database)(implicit slickExecutionContext: ExecutionContext) extends ActionBuilder[DBSessionRequest] {

  override def executionContext = slickExecutionContext

  override def invokeBlock[A](request: Request[A], block: DBSessionRequest[A] => Future[SimpleResult]): Future[SimpleResult] = {
    slickDatabase.withSession { session =>
      block(DBSessionRequest(session, request))
    }
  }
}
