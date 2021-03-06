package controllers

import akka.http.scaladsl.model.MediaTypes
import akka.util.ByteString
import com.typesafe.scalalogging.StrictLogging
import javax.inject._
import models.Project
import play.api.mvc._
import util._
import util.Util._

import scala.concurrent.Future

@Singleton
class ProjectController @Inject()(secured: SecuredAction,
                                  cc: ControllerComponents,
                                  cacheController: CacheController)(implicit ec: DatabaseExecutionContext)
  extends AbstractController(cc) with StrictLogging with JsonValidation {

  def list: Action[AnyContent] = secured.async { req =>
    Future {
      println("---->>"+(Project.jsonContext.toJson(Project.find().findList())))
      Ok(Project.jsonContext.toJson(Project.find().findList())).as(MediaTypes.`application/json`.value)
    }
  }

  def update: Action[ByteString] = secured.async(parse.byteString) { req =>
    val el: Project = Project.jsonContext.toBean(classOf[Project], fromBytes(req.body.toArray))
    Future {
      el.id match {
        case _: Any => Project.update(el)
        case _ => Project.insert(el)
      }
      Ok(Project.jsonContext.toJson(el)).as(MediaTypes.`application/json`.value)
    }
  }

  def byId(id: Long): Action[AnyContent] = secured.async { _ =>
    Future {
      Ok(Project.jsonContext.toJson(
        Project.find()
          .fetch("risks")
          .fetch("tasks")
          .fetch("strategies")
          .fetch("budget")
          .where().eq("id", id)
          .findOne()
      )).as(MediaTypes.`application/json`.value)
    }
  }
}
