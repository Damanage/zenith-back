package controllers

import akka.http.scaladsl.model.MediaTypes
import akka.util.ByteString
import com.typesafe.scalalogging.StrictLogging
import javax.inject._
import models.Email
import play.api.mvc._
import util.Util._
import util._

import scala.concurrent.Future

@Singleton
class EmailController @Inject()(secured: SecuredAction,
                                cc: ControllerComponents,
                                cacheController: CacheController)(implicit ec: DatabaseExecutionContext)
  extends AbstractController(cc) with StrictLogging with JsonValidation {

  def list: Action[AnyContent] = secured.async { req =>
    Future {
      Ok(Email.jsonContext.toJson(Email.find().findList())).as(MediaTypes.`application/json`.value)
    }
  }

  def update: Action[ByteString] = secured.async(parse.byteString) { req =>
    val el: Email = Email.jsonContext.toBean(classOf[Email], fromBytes(req.body.toArray))
    Future {
      el.id match {
        case _: Any => Email.update(el)
        case _ => Email.insert(el)
      }
      Ok(Email.jsonContext.toJson(el)).as(MediaTypes.`application/json`.value)
    }
  }

  def byId(id: Long): Action[AnyContent] = secured.async { _ =>
    Future {
      Ok(Email.jsonContext.toJson(
        Email.find()
          .fetch("legalEntity")
          .fetch("individual")
          .where().eq("id", id)
          .findOne()
      )).as(MediaTypes.`application/json`.value)
    }
  }
}
