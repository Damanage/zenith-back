package controllers

import akka.http.scaladsl.model.MediaTypes
import akka.util.ByteString
import com.typesafe.scalalogging.StrictLogging
import javax.inject._
import models.Forcast
import play.api.mvc._
import util.Util._
import util._

import scala.concurrent.Future

@Singleton
class ForcastController @Inject()(secured: SecuredAction,
                                  cc: ControllerComponents,
                                  cacheController: CacheController)(implicit ec: DatabaseExecutionContext)
  extends AbstractController(cc) with StrictLogging with JsonValidation {

  def list: Action[AnyContent] = secured.async { req =>
    Future {
      Ok(Forcast.jsonContext.toJson(Forcast.find().findList())).as(MediaTypes.`application/json`.value)
    }
  }

  def update: Action[ByteString] = secured.async(parse.byteString) { req =>
    val el: Forcast = Forcast.jsonContext.toBean(classOf[Forcast], fromBytes(req.body.toArray))
    Future {
      el.id match {
        case _: Any => Forcast.update(el)
        case _ => Forcast.insert(el)
      }
      Ok(Forcast.jsonContext.toJson(el)).as(MediaTypes.`application/json`.value)
    }
  }

  def byId(id: Long): Action[AnyContent] = secured.async { _ =>
    Future {
      Ok(Forcast.jsonContext.toJson(
        Forcast.find()
          .fetch("creditAgreement")
          .where().eq("id", id)
          .findOne()
      )).as(MediaTypes.`application/json`.value)
    }
  }
}
