package util

import javax.inject.Inject
import models.User
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

class SecuredUserRequest[A](val user: User, request: Request[A]) extends WrappedRequest[A](request)

class SecuredAction @Inject()(cacheController: CacheController, val parser: BodyParsers.Default)(implicit val executionContext: ExecutionContext)
  extends ActionBuilder[SecuredUserRequest, AnyContent] {
  override def invokeBlock[A](request: Request[A], block: SecuredUserRequest[A] => Future[Result]): Future[Result] = {
    request.headers.get(CacheController.HEADER_TOKEN).flatMap(cacheController.getSessionUser) match {
      case Some(user) =>
        block(new SecuredUserRequest(user, request))
      case None => Future.successful(Results.Unauthorized("Неверные логин или пароль."))
    }
  }
}
