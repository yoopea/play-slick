package play.api.db.slick.plugin

import play.api.Plugin
import play.api.db.slick.SlickExecutionContexts
import play.api.Application
import akka.actor.ActorSystem
import play.api.Play
import com.typesafe.config.ConfigFactory

/**
 * Needed only because there is no way to 'attach' a new execution context on an existing actorsystem
 */
class SlickAkkaPlugin(app: Application) extends Plugin {

  var slickActorSystem: ActorSystem = null 
  
  def isEnabled: Boolean = app.configuration.getConfig(SlickExecutionContexts.defaultConfigSection).isDefined

  override def onStart(): Unit = {
    if (enabled) {  
      slickActorSystem = ActorSystem("play-slick", ConfigFactory.load(Thread.currentThread().getContextClassLoader(), "play-slick"))
      
    }
  }

  override def onStop(): Unit = {
    if (enabled) {
      Play.logger.info("Shutting down play-slick actor system..." )
      slickActorSystem.shutdown()
      slickActorSystem.awaitTermination()
    }
  }

}