package util

import java.util.UUID
import java.util.concurrent.TimeUnit

import akka.actor.{ActorRef, ActorSystem}
import com.google.common.cache.{Cache, CacheBuilder}
import javax.inject.{Inject, Singleton}
import models.User
import org.apache.commons.codec.digest.DigestUtils
import play.api.libs.concurrent.CustomExecutionContext

import scala.concurrent.Future

class CacheExecutionContext @Inject()(actorSystem: ActorSystem) extends CustomExecutionContext(actorSystem, "repository.dispatcher")

@Singleton
class CacheController @Inject()(actorSystem: ActorSystem)
                               (implicit ec: CacheExecutionContext) {

  import CacheController._

  initCache(actorSystem)

  def authenticate(authRequest: AuthRequest): Future[AuthResponse] = {
    Future {
      val usr = User.find().where().eq("login", authRequest.username).findOne()
      if (usr != null && usr.getPassword == DigestUtils.sha256Hex(authRequest.password)) {
        val token: String = UUID.randomUUID.toString
        putToCache(token, usr)
        AuthResponse(HEADER_TOKEN, Some(token), Some(usr))
      } else {
        AuthResponse("", None, None)
      }
    }
  }

  def getSessionUser(token: String): Option[User] = {
    getFromCache(token)
  }
}

object CacheController {
  final val HEADER_TOKEN: String = "X-RANDOMIZE-TOKEN"
  private var underlyingCache: Cache[String, User] = _

  def initCache(system: ActorSystem): Unit = {
    underlyingCache = CacheBuilder.newBuilder().maximumSize(10000L)
      .expireAfterAccess(30, TimeUnit.MINUTES).build[String, User]
  }

  def putToCache(token: String, u: User): User = {
    underlyingCache.put(token, u)
    u
  }

  def invalidate(token: String): Unit = {
    underlyingCache.invalidate(token)
  }

  def getFromCache(token: String): Option[User] = {
    Option(underlyingCache.getIfPresent(token))
  }
}
