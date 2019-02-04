package models

import java.util.Date

import io.ebean.annotation.WhenCreated
import javax.persistence._

import scala.beans.BeanProperty

@Entity
@Table(name = "credit_schedule")
class CreditSchedule extends BaseEntity {
  @BeanProperty
  var schedule_type: String = _
  @BeanProperty
  var payment_number: String = _
  @BeanProperty
  var payment_date: Date = _
  @BeanProperty
  var currency: String = _
  @BeanProperty
  var payment_od: JBigInt = _
  @BeanProperty
  var payment_percent: JBigInt = _
  @BeanProperty
  var payment_comission: JBigInt = _
  @BeanProperty
  var payment_penni: JBigInt = _

  @BeanProperty
  @ManyToOne
  @JoinColumn(name="doc_id", nullable=false)
  var creditAgreement: CreditAgreement = _
}

object CreditSchedule extends Dao(classOf[CreditSchedule]) {
}








