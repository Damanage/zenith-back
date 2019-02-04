package util

import akka.actor.ActorSystem
import javax.inject.{Inject, Singleton}
import play.api.libs.concurrent.CustomExecutionContext
import play.db.ebean.EbeanDynamicEvolutions


@Singleton
class DatabaseExecutionContext @Inject()(actorSystem: ActorSystem, ebeanDynamicEvolutions: EbeanDynamicEvolutions) extends CustomExecutionContext(actorSystem, "db.dispatcher")
