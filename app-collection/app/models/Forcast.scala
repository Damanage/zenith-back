package models

import java.util.Date

import io.ebean.annotation.WhenCreated
import javax.persistence._

import scala.beans.BeanProperty

@Entity
@Table(name = "forcast")
class Forcast extends BaseEntity {
  @BeanProperty
  var forcast_date: Date = _
  @BeanProperty
  var forcast_real: JBigInt = _
  @BeanProperty
  var forcast_pessim: JBigInt = _
  @BeanProperty
  var forcast_optim: JBigInt = _

  @BeanProperty
  @ManyToOne
  @JoinColumn(name="doc_id", nullable=false)
  var creditAgreement: CreditAgreement = _
}

object Forcast extends Dao(classOf[Forcast]) {
}








