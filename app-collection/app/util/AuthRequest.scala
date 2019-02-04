package util

import play.api.libs.json.{Json, OFormat}

case class AuthRequest(username: String, password: String)
object AuthRequest{
  implicit val jsonFormat: OFormat[AuthRequest] = Json.format[AuthRequest]
}
