package models

import java.util.Date

import javax.persistence._

import scala.beans.BeanProperty

@Entity
@Table(name = "credit_agreement")
class CreditAgreement extends BaseEntity {
  @BeanProperty
  var doc_number: String = _
  @BeanProperty
  var doc_status: String = _
  @BeanProperty
  var sum_delay_od: JBigInt = _
  @BeanProperty
  var sum_delay_percent: JBigInt = _
  @BeanProperty
  var sum_delay_commission: JBigInt = _
  @BeanProperty
  var sum_penni: JBigInt = _
  @BeanProperty
  var current_balance: JBigInt = _
  @BeanProperty
  var current_debt: JBigInt = _
  @BeanProperty
  var currency: String = _
  @BeanProperty
  var date_delay: Date = _
  @BeanProperty
  var doc_date: Date = _
  @BeanProperty
  var doc_close_date: Date = _
  @BeanProperty
  var product: String = _
  @BeanProperty
  var date_next_payment: Date = _
  @BeanProperty
  var sum_next_payment: JBigInt = _
  @BeanProperty
  var date_last_payment: Date = _
  @BeanProperty
  var sum_last_payment: JBigInt = _

  @BeanProperty
  @OneToMany(mappedBy = "creditAgreement")
  var attachments: JList[Attachment] = _

  @BeanProperty
  @OneToMany(mappedBy = "creditAgreement")
  var schedules: JList[CreditSchedule] = _

  @BeanProperty
  @OneToMany(mappedBy = "creditAgreement")
  var pledgedocs: JList[PledgeAgreement] = _

  @BeanProperty
  @OneToMany(mappedBy = "creditAgreement")
  var forcasts: JList[Forcast] = _
}

object CreditAgreement extends Dao(classOf[CreditAgreement]) {
}









