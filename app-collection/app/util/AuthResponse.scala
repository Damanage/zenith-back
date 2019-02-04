package util

import models.User
import play.api.libs.json.{Json, OFormat}

case class AuthResponse(header: String, token: Option[String], user: Option[User])

object AuthResponse {
  implicit val jsonFormat: OFormat[AuthResponse] = Json.format[AuthResponse]
}