package controllers

import akka.http.scaladsl.model.MediaTypes
import akka.util.ByteString
import com.typesafe.scalalogging.StrictLogging
import javax.inject._
import models.Individual
import play.api.mvc._
import util.Util._
import util._

import scala.concurrent.Future

@Singleton
class IndividualController @Inject()(secured: SecuredAction,
                                     cc: ControllerComponents,
                                     cacheController: CacheController)(implicit ec: DatabaseExecutionContext)
  extends AbstractController(cc) with StrictLogging with JsonValidation {

  def list: Action[AnyContent] = secured.async { req =>
    Future {
      Ok(Individual.jsonContext.toJson(Individual.find().findList())).as(MediaTypes.`application/json`.value)
    }
  }

  def update: Action[ByteString] = secured.async(parse.byteString) { req =>
    val el: Individual = Individual.jsonContext.toBean(classOf[Individual], fromBytes(req.body.toArray))
    Future {
      el.id match {
        case _: Any => Individual.update(el)
        case _ => Individual.insert(el)
      }
      Ok(Individual.jsonContext.toJson(el)).as(MediaTypes.`application/json`.value)
    }
  }

  def byId(id: Long): Action[AnyContent] = secured.async { _ =>
    Future {
      Ok(Individual.jsonContext.toJson(
        Individual.find()
          .fetch("project")
          .fetch("addresses")
          .fetch("telephones")
          .fetch("emails")
          .where().eq("id", id)
          .findOne()
      )).as(MediaTypes.`application/json`.value)
    }
  }
}
