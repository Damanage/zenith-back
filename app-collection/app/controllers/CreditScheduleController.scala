package controllers

import akka.http.scaladsl.model.MediaTypes
import akka.util.ByteString
import com.typesafe.scalalogging.StrictLogging
import javax.inject._
import models.CreditSchedule
import play.api.mvc._
import util.Util._
import util._

import scala.concurrent.Future

@Singleton
class CreditScheduleController @Inject()(secured: SecuredAction,
                                         cc: ControllerComponents,
                                         cacheController: CacheController)(implicit ec: DatabaseExecutionContext)
  extends AbstractController(cc) with StrictLogging with JsonValidation {

  def list: Action[AnyContent] = secured.async { req =>
    Future {
      Ok(CreditSchedule.jsonContext.toJson(CreditSchedule.find().findList())).as(MediaTypes.`application/json`.value)
    }
  }

  def update: Action[ByteString] = secured.async(parse.byteString) { req =>
    val el: CreditSchedule = CreditSchedule.jsonContext.toBean(classOf[CreditSchedule], fromBytes(req.body.toArray))
    Future {
      el.id match {
        case _: Any => CreditSchedule.update(el)
        case _ => CreditSchedule.insert(el)
      }
      Ok(CreditSchedule.jsonContext.toJson(el)).as(MediaTypes.`application/json`.value)
    }
  }

  def byId(id: Long): Action[AnyContent] = secured.async { _ =>
    Future {
      Ok(CreditSchedule.jsonContext.toJson(
        CreditSchedule.find()
          .fetch("creditAgreement")
          .where().eq("id", id)
          .findOne()
      )).as(MediaTypes.`application/json`.value)
    }
  }
}
