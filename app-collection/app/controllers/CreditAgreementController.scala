package controllers

import akka.http.scaladsl.model.MediaTypes
import akka.util.ByteString
import com.typesafe.scalalogging.StrictLogging
import javax.inject._
import models.CreditAgreement
import play.api.mvc._
import util.Util._
import util._

import scala.concurrent.Future

@Singleton
class CreditAgreementController @Inject()(secured: SecuredAction,
                                          cc: ControllerComponents,
                                          cacheController: CacheController)(implicit ec: DatabaseExecutionContext)
  extends AbstractController(cc) with StrictLogging with JsonValidation {

  def list: Action[AnyContent] = secured.async { req =>
    Future {
      Ok(CreditAgreement.jsonContext.toJson(CreditAgreement.find().findList())).as(MediaTypes.`application/json`.value)
    }
  }

  def update: Action[ByteString] = secured.async(parse.byteString) { req =>
    val el: CreditAgreement = CreditAgreement.jsonContext.toBean(classOf[CreditAgreement], fromBytes(req.body.toArray))
    Future {
      el.id match {
        case _: Any => CreditAgreement.update(el)
        case _ => CreditAgreement.insert(el)
      }
      Ok(CreditAgreement.jsonContext.toJson(el)).as(MediaTypes.`application/json`.value)
    }
  }

  def byId(id: Long): Action[AnyContent] = secured.async { _ =>
    Future {
      Ok(CreditAgreement.jsonContext.toJson(
        CreditAgreement.find()
          .fetch("attachments")
          .fetch("schedules")
          .fetch("pledgedocs")
          .fetch("forcasts")
          .where().eq("id", id)
          .findOne()
      )).as(MediaTypes.`application/json`.value)
    }
  }
}
