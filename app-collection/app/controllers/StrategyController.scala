package controllers

import akka.http.scaladsl.model.MediaTypes
import akka.util.ByteString
import com.typesafe.scalalogging.StrictLogging
import javax.inject._
import models.Strategy
import play.api.mvc._
import util.Util._
import util._

import scala.concurrent.Future

@Singleton
class StrategyController @Inject()(secured: SecuredAction,
                                   cc: ControllerComponents,
                                   cacheController: CacheController)(implicit ec: DatabaseExecutionContext)
  extends AbstractController(cc) with StrictLogging with JsonValidation {

  def list: Action[AnyContent] = secured.async { req =>
    Future {
      Ok(Strategy.jsonContext.toJson(Strategy.find().findList())).as(MediaTypes.`application/json`.value)
    }
  }

  def update: Action[ByteString] = secured.async(parse.byteString) { req =>
    val el: Strategy = Strategy.jsonContext.toBean(classOf[Strategy], fromBytes(req.body.toArray))
    Future {
      el.id match {
        case _: Any => Strategy.update(el)
        case _ => Strategy.insert(el)
      }
      Ok(Strategy.jsonContext.toJson(el)).as(MediaTypes.`application/json`.value)
    }
  }

  def byId(id: Long): Action[AnyContent] = secured.async { _ =>
    Future {
      Ok(Strategy.jsonContext.toJson(
        Strategy.find()
          .fetch("project")
          .where().eq("id", id)
          .findOne()
      )).as(MediaTypes.`application/json`.value)
    }
  }
}
