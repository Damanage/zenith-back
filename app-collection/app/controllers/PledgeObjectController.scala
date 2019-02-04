package controllers

import akka.http.scaladsl.model.MediaTypes
import akka.util.ByteString
import com.typesafe.scalalogging.StrictLogging
import javax.inject._
import models.PledgeObject
import play.api.mvc._
import util.Util._
import util._

import scala.concurrent.Future

@Singleton
class PledgeObjectController @Inject()(secured: SecuredAction,
                                       cc: ControllerComponents,
                                       cacheController: CacheController)(implicit ec: DatabaseExecutionContext)
  extends AbstractController(cc) with StrictLogging with JsonValidation {

  def list: Action[AnyContent] = secured.async { req =>
    Future {
      Ok(PledgeObject.jsonContext.toJson(PledgeObject.find().findList())).as(MediaTypes.`application/json`.value)
    }
  }

  def update: Action[ByteString] = secured.async(parse.byteString) { req =>
    val el: PledgeObject = PledgeObject.jsonContext.toBean(classOf[PledgeObject], fromBytes(req.body.toArray))
    Future {
      el.id match {
        case _: Any => PledgeObject.update(el)
        case _ => PledgeObject.insert(el)
      }
      Ok(PledgeObject.jsonContext.toJson(el)).as(MediaTypes.`application/json`.value)
    }
  }

  def byId(id: Long): Action[AnyContent] = secured.async { _ =>
    Future {
      Ok(PledgeObject.jsonContext.toJson(
        PledgeObject.find()
          .fetch("pledgedocs")
          .where().eq("id", id)
          .findOne()
      )).as(MediaTypes.`application/json`.value)
    }
  }
}
