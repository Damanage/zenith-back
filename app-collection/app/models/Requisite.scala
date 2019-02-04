package models

import java.util.Date

import io.ebean.annotation.WhenCreated
import javax.persistence.{Entity, JoinColumn, OneToOne, Table}

import scala.beans.BeanProperty

@Entity
@Table(name = "requisite")
class Requisite extends BaseEntity {
  @BeanProperty
  var full_name: String = _
  @BeanProperty
  var inn: String = _
  @BeanProperty
  var ogrn: String = _
  @BeanProperty
  var cor_account: String = _
  @BeanProperty
  var account: String = _
  @BeanProperty
  var bank: String = _
  @BeanProperty
  var bik: String = _
}

object Requisite extends Dao(classOf[Requisite]) {
}









