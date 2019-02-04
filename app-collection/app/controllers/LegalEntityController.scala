package controllers

import akka.http.scaladsl.model.MediaTypes
import akka.util.ByteString
import com.typesafe.scalalogging.StrictLogging
import javax.inject._
import models.LegalEntity
import play.api.mvc._
import util.Util._
import util._

import scala.concurrent.Future

@Singleton
class LegalEntityController @Inject()(secured: SecuredAction,
                                      cc: ControllerComponents,
                                      cacheController: CacheController)(implicit ec: DatabaseExecutionContext)
  extends AbstractController(cc) with StrictLogging with JsonValidation {

  def list: Action[AnyContent] = secured.async { req =>
    Future {
      Ok(LegalEntity.jsonContext.toJson(LegalEntity.find().findList())).as(MediaTypes.`application/json`.value)
    }
  }

  def update: Action[ByteString] = secured.async(parse.byteString) { req =>
    val el: LegalEntity = LegalEntity.jsonContext.toBean(classOf[LegalEntity], fromBytes(req.body.toArray))
    Future {
      el.id match {
        case _: Any => LegalEntity.update(el)
        case _ => LegalEntity.insert(el)
      }
      Ok(LegalEntity.jsonContext.toJson(el)).as(MediaTypes.`application/json`.value)
    }
  }

  def byId(id: Long): Action[AnyContent] = secured.async { _ =>
    Future {
      Ok(LegalEntity.jsonContext.toJson(
        LegalEntity.find()
          .fetch("project")
          .fetch("addresses")
          .fetch("telephones")
          .fetch("emails")
          .where().eq("id", id)
          .findOne()
      )).as(MediaTypes.`application/json`.value)
    }
  }
}
