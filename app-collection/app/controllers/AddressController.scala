package controllers

import akka.http.scaladsl.model.MediaTypes
import akka.util.ByteString
import com.typesafe.scalalogging.StrictLogging
import javax.inject._
import models.Address
import play.api.mvc._
import util.Util._
import util._

import scala.concurrent.Future

@Singleton
class AddressController @Inject()(secured: SecuredAction,
                                  cc: ControllerComponents,
                                  cacheController: CacheController)(implicit ec: DatabaseExecutionContext)
  extends AbstractController(cc) with StrictLogging with JsonValidation {

  def list: Action[AnyContent] = secured.async { req =>
    Future {
      Ok(Address.jsonContext.toJson(Address.find().findList())).as(MediaTypes.`application/json`.value)
    }
  }

  def update: Action[ByteString] = secured.async(parse.byteString) { req =>
    val el: Address = Address.jsonContext.toBean(classOf[Address], fromBytes(req.body.toArray))
    Future {
      el.id match {
        case _: Any => Address.update(el)
        case _ => Address.insert(el)
      }
      Ok(Address.jsonContext.toJson(el)).as(MediaTypes.`application/json`.value)
    }
  }

  def byId(id: Long): Action[AnyContent] = secured.async { _ =>
    Future {
      Ok(Address.jsonContext.toJson(
        Address.find()
          .fetch("legalEntity")
          .fetch("individual")
          .where().eq("id", id)
          .findOne()
      )).as(MediaTypes.`application/json`.value)
    }
  }
}
