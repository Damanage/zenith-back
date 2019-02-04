package models

import java.util.Date

import javax.persistence._

import scala.beans.BeanProperty

@Entity
@Table(name = "pledge_agreement")
class PledgeAgreement extends BaseEntity {
  @BeanProperty
  var doc_number: String = _
  @BeanProperty
  var doc_date: Date = _
  @BeanProperty
  var doc_close_date: Date = _
  @BeanProperty
  var pledger: String = _
  @BeanProperty
  var pledge_status: String = _
  @BeanProperty
  var pledge_price: JBigInt = _
  @BeanProperty
  var pledge_real_price: JBigInt = _

  @BeanProperty
  @ManyToOne
  @JoinColumn(name="doc_id", nullable=false)
  var creditAgreement: CreditAgreement = _

  @BeanProperty
  @ManyToMany(mappedBy = "pledgedocs")
  var pledgeobjects: JList[PledgeObject] = _
}

object PledgeAgreement extends Dao(classOf[PledgeAgreement]) {
}




