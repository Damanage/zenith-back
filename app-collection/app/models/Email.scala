package models

import java.util.Date

import io.ebean.annotation.WhenCreated
import javax.persistence._

import scala.beans.BeanProperty

@Entity
@Table(name = "email")
class Email extends BaseEntity {
  @BeanProperty
  var mail_addr: String = _
  @BeanProperty
  var main_flg: JInt = _
  @BeanProperty
  var email_status: String = _
  @BeanProperty
  @WhenCreated
  var created: Date = _
  @OneToOne
  @JoinColumn(name = "author_id")
  @BeanProperty
  var author: User = _

  @BeanProperty
  @ManyToOne
  @JoinColumn(name = "legal_entity_id")
  var legalEntity: LegalEntity = _

  @BeanProperty
  @ManyToOne
  @JoinColumn(name = "individual_id")
  var individual: Individual = _
}

object Email extends Dao(classOf[Email]) {
}






