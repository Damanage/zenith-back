import com.google.inject.AbstractModule
import net.codingwell.scalaguice.ScalaModule
import play.api.{Configuration, Environment}
import util.{CacheController, DatabaseExecutionContext}


class Module(environment: Environment, configuration: Configuration)
  extends AbstractModule with ScalaModule {

  override def configure(): Unit = {
    bind(classOf[DatabaseExecutionContext]).asEagerSingleton()
    bind(classOf[CacheController]).asEagerSingleton()
  }
}