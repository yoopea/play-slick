package play.api.db.slick

import play.api.libs.concurrent.Akka
import akka.actor.ActorSystem
import play.api.Application
import com.typesafe.config.ConfigFactory

object SlickExecutionContexts {

  /* Used to control the parallelism of the database connection 
   * 
   * Will be removed whenever Slick becomes async
   * */
  def executionContext(app: Application) = {
    val configSection = "play.akka.actor.slick-context"
    app.configuration.getConfig(configSection) match {
      case Some(_) => Akka.system(app).dispatchers.lookup(configSection)
      case None =>
        ActorSystem("other-system").dispatchers.lookup(configSection)
    }
  }

}