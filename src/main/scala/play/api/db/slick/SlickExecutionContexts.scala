package play.api.db.slick

import play.api.libs.concurrent.Akka
import akka.actor.ActorSystem
import play.api.Application
import com.typesafe.config.ConfigFactory
import play.api.db.slick.plugin.SlickAkkaPlugin
import akka.dispatch.ForkJoinExecutorConfigurator
import play.api.Logger

object SlickExecutionContexts {

  val defaultConfigSection = "play.akka.actor.slick-context"

  /* Used to control the parallelism of the database connection 
   * 
   * Will be removed whenever Slick becomes async
   * */
  def executionContext(app: Application) = {
    app.configuration.getConfig(defaultConfigSection) match {
      case Some(_) => Akka.system(app).dispatchers.lookup(defaultConfigSection)
      case None =>
        Logger.info("loading the default play-slick excecution context")
        val slickActorSystem = app.plugin[SlickAkkaPlugin].map(_.slickActorSystem).getOrElse {
          sys.error("The SlickAkka plugin is not registered. Either register plugin by adding '<priority>:play.api.db.slick.plugin.SlickAkkaPlugin' in a play.plugins file on the classpath or define a dispatcher in your appliction.conf in a section called: play.akka.actor.slick-context.")
        }
        slickActorSystem.dispatchers.lookup(defaultConfigSection)
    }
  }

}