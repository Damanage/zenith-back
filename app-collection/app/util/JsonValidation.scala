package util

import play.api.libs.json.{JsError, Reads}
import play.api.mvc.{BaseControllerHelpers, BodyParser}

import scala.concurrent.ExecutionContext

trait JsonValidation {
  self: BaseControllerHelpers =>
  def validateJson[A: Reads](implicit ec: ExecutionContext): BodyParser[A] = parse.json.validate(
    _.validate[A].asEither.left.map(e => BadRequest(JsError.toJson(e)))
  )
}
