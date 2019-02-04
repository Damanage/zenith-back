package models

import java.util.Date

import javax.persistence._
import annotation.elidable._
import scala.beans.BeanProperty

@Entity
@Table(name = "pledge_object")
class PledgeObject extends BaseEntity {
  @BeanProperty
  var pledge_type: String = _
  @BeanProperty
  var pledge_kind: String = _
  @BeanProperty
  var pledge_status: String = _
  @BeanProperty
  var balance_holder: String = _
  @BeanProperty
  var balance_price: JBigInt = _
  @BeanProperty
  var balance_price_first: JBigInt = _
  @BeanProperty
  var work_date: Date = _
  @BeanProperty
  var pledge_category: String = _

  @BeanProperty
  @ManyToMany
  @JoinTable(
    name = "pledge_agreement_object",
    joinColumns = Array(new JoinColumn(name = "pledge_object_id", referencedColumnName = "id")),
  inverseJoinColumns = Array(new JoinColumn(name = "pledge_agreement_id", referencedColumnName = "id")))
  var pledgedocs: JList[PledgeAgreement] = _
}

object PledgeObject extends Dao(classOf[PledgeObject]) {
}








