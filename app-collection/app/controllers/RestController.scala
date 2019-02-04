package controllers

import javax.inject._
import play.api.libs.json.Json
import play.api.mvc._
import util._

@Singleton
class RestController @Inject()(cc: ControllerComponents,
                               cacheController: CacheController)(implicit ec: DatabaseExecutionContext)
  extends AbstractController(cc) with JsonValidation {

  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index(""))
  }

  def health = Action { _ =>
    Ok
  }

  def signIn: Action[AuthRequest] = Action(validateJson[AuthRequest]).async { req =>
    val auth = req.body
    cacheController.authenticate(auth).map {
      result => Ok(Json.toJson(result))
    }
  }

  def logOut = Action { req =>
    req.headers.get(CacheController.HEADER_TOKEN).foreach(CacheController.invalidate)
    Ok("OK")
  }
}
