package controllers

import akka.http.scaladsl.model.MediaTypes
import akka.util.ByteString
import com.typesafe.scalalogging.StrictLogging
import javax.inject._
import models.Budget
import play.api.mvc._
import util.Util._
import util._

import scala.concurrent.Future

@Singleton
class BudgetController @Inject()(secured: SecuredAction,
                                 cc: ControllerComponents,
                                 cacheController: CacheController)(implicit ec: DatabaseExecutionContext)
  extends AbstractController(cc) with StrictLogging with JsonValidation {

  def list: Action[AnyContent] = secured.async { req =>
    Future {
      Ok(Budget.jsonContext.toJson(Budget.find().findList())).as(MediaTypes.`application/json`.value)
    }
  }

  def update: Action[ByteString] = secured.async(parse.byteString) { req =>
    val el: Budget = Budget.jsonContext.toBean(classOf[Budget], fromBytes(req.body.toArray))
    Future {
      el.id match {
        case _: Any => Budget.update(el)
        case _ => Budget.insert(el)
      }
      Ok(Budget.jsonContext.toJson(el)).as(MediaTypes.`application/json`.value)
    }
  }

  def byId(id: Long): Action[AnyContent] = secured.async { _ =>
    Future {
      Ok(Budget.jsonContext.toJson(
        Budget.find()
          .fetch("project")
          .where().eq("id", id)
          .findOne()
      )).as(MediaTypes.`application/json`.value)
    }
  }
}
