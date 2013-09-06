package play.api.db.slick

import play.api.Application
import scala.concurrent.ExecutionContext

object DBAction extends WithDB(Config.defaultDatabase(play.api.Play.current))(SlickExecutionContexts.executionContext(play.api.Play.current)) { //<-- Play.current
  def using(database: Database)(implicit executionContext: ExecutionContext) = new WithDB(database)
  
  def using(sourceProvider: DBSourceProvider)(implicit executionContext: ExecutionContext) = new WithDB(Database(sourceProvider))
  
  def forName(name: String, app: Application = play.api.Play.current) = new WithDB(Config.database(name)(app))(SlickExecutionContexts.executionContext(play.api.Play.current)) //<--- Play.current
}