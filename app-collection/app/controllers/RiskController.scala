package controllers

import akka.http.scaladsl.model.MediaTypes
import akka.util.ByteString
import com.typesafe.scalalogging.StrictLogging
import javax.inject._
import models.Risk
import play.api.mvc._
import util.Util._
import util._

import scala.concurrent.Future

@Singleton
class RiskController @Inject()(secured: SecuredAction,
                               cc: ControllerComponents,
                               cacheController: CacheController)(implicit ec: DatabaseExecutionContext)
  extends AbstractController(cc) with StrictLogging with JsonValidation {

  def list: Action[AnyContent] = secured.async { req =>
    Future {
      Ok(Risk.jsonContext.toJson(Risk.find().findList())).as(MediaTypes.`application/json`.value)
    }
  }

  def update: Action[ByteString] = secured.async(parse.byteString) { req =>
    val el: Risk = Risk.jsonContext.toBean(classOf[Risk], fromBytes(req.body.toArray))
    Future {
      el.id match {
        case _: Any => Risk.update(el)
        case _ => Risk.insert(el)
      }
      Ok(Risk.jsonContext.toJson(el)).as(MediaTypes.`application/json`.value)
    }
  }

  def byId(id: Long): Action[AnyContent] = secured.async { _ =>
    Future {
      Ok(Risk.jsonContext.toJson(
        Risk.find()
          .fetch("project")
          .where().eq("id", id)
          .findOne()
      )).as(MediaTypes.`application/json`.value)
    }
  }
}
