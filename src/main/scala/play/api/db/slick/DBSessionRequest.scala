package play.api.db.slick

import play.api.mvc.Request
import play.api.mvc.WrappedRequest

case class DBSessionRequest[A](slickSession: Session, request: Request[A]) extends WrappedRequest[A](request)
