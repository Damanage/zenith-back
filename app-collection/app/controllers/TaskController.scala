package controllers

import akka.http.scaladsl.model.MediaTypes
import akka.util.ByteString
import com.typesafe.scalalogging.StrictLogging
import javax.inject._
import models.Task
import play.api.mvc._
import util.Util._
import util._

import scala.concurrent.Future

@Singleton
class TaskController @Inject()(secured: SecuredAction,
                               cc: ControllerComponents,
                               cacheController: CacheController)(implicit ec: DatabaseExecutionContext)
  extends AbstractController(cc) with StrictLogging with JsonValidation {

  def list: Action[AnyContent] = secured.async { req =>
    Future {
      Ok(Task.jsonContext.toJson(Task.find().findList())).as(MediaTypes.`application/json`.value)
    }
  }

  def update: Action[ByteString] = secured.async(parse.byteString) { req =>
    val el: Task = Task.jsonContext.toBean(classOf[Task], fromBytes(req.body.toArray))
    Future {
      el.id match {
        case _: Any => Task.update(el)
        case _ => Task.insert(el)
      }
      Ok(Task.jsonContext.toJson(el)).as(MediaTypes.`application/json`.value)
    }
  }

  def byId(id: Long): Action[AnyContent] = secured.async { _ =>
    Future {
      Ok(Task.jsonContext.toJson(
        Task.find()
          .fetch("project")
          .where().eq("id", id)
          .findOne()
      )).as(MediaTypes.`application/json`.value)
    }
  }
}
