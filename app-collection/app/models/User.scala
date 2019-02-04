package models

import javax.persistence.{Entity, FetchType, OneToMany, Table}
import play.api.libs.json.{Json, OFormat}

import scala.beans.BeanProperty

@Entity
@Table(name = "employee")
class User extends BaseEntity {
  @BeanProperty
  var sur_name: String = _
  @BeanProperty
  var user_name: String = _
  @BeanProperty
  var patr_name: String = _
  @BeanProperty
  var login: String = _
  @BeanProperty
  var password: String = _
  @BeanProperty
  var role: String = _
  @BeanProperty
  var position: String = _
  @BeanProperty
  var department: String = _
  @BeanProperty
  var user_number: String = _
  @BeanProperty
  var user_status: String = _
  @BeanProperty
  var head_fio: String = _
}

object User extends Dao(classOf[User]) {
  def apply(id: Long,
            sur_name: String,
            user_name: String,
            patr_name: String,
            login: String,
            password: String,
            role: String,
            position: String,
            department: String,
            user_number: String,
            user_status: String,
            head_fio: String): User = {
    val usr = new User()
    usr.setId(id)
    usr.setSur_name(sur_name)
    usr.setUser_name(user_name)
    usr.setPatr_name(patr_name)
    usr.setLogin(login)
    usr.setPassword(password)
    usr.setRole(role)
    usr.setPosition(position)
    usr.setDepartment(department)
    usr.setUser_number(user_number)
    usr.setUser_status(user_status)
    usr.setHead_fio(head_fio)
    usr
  }

  def unapply(usr: User): Option[(Long,
    String, String,
    String, String,
    String, String,
    String, String,
    String, String,
    String)] =
    Some((usr.getId,
      usr.getSur_name,
      usr.getUser_name,
      usr.getPatr_name,
      usr.getLogin,
      usr.getPassword,
      usr.getRole, usr.getPosition,
      usr.getDepartment, usr.getUser_number,
      usr.getUser_status, usr.getHead_fio))

  implicit val jsonFormat: OFormat[User] = Json.format[User]
}
