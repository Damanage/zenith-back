package controllers

import akka.http.scaladsl.model.MediaTypes
import akka.util.ByteString
import com.typesafe.scalalogging.StrictLogging
import javax.inject._
import models.Telephone
import play.api.mvc._
import util.Util._
import util._

import scala.concurrent.Future

@Singleton
class TelephoneController @Inject()(secured: SecuredAction,
                                    cc: ControllerComponents,
                                    cacheController: CacheController)(implicit ec: DatabaseExecutionContext)
  extends AbstractController(cc) with StrictLogging with JsonValidation {

  def list: Action[AnyContent] = secured.async { req =>
    Future {
      Ok(Telephone.jsonContext.toJson(Telephone.find().findList())).as(MediaTypes.`application/json`.value)
    }
  }

  def update: Action[ByteString] = secured.async(parse.byteString) { req =>
    val el: Telephone = Telephone.jsonContext.toBean(classOf[Telephone], fromBytes(req.body.toArray))
    Future {
      el.id match {
        case _: Any => Telephone.update(el)
        case _ => Telephone.insert(el)
      }
      Ok(Telephone.jsonContext.toJson(el)).as(MediaTypes.`application/json`.value)
    }
  }

  def byId(id: Long): Action[AnyContent] = secured.async { _ =>
    Future {
      Ok(Telephone.jsonContext.toJson(
        Telephone.find()
          .fetch("legalEntity")
          .fetch("individual")
          .where().eq("id", id)
          .findOne()
      )).as(MediaTypes.`application/json`.value)
    }
  }
}
