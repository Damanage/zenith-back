package models

import java.util.Date

import io.ebean.annotation.WhenCreated
import javax.persistence._

import scala.beans.BeanProperty

@Entity
@Table(name = "budget")
class Budget extends BaseEntity {
  @BeanProperty
  var budget_code: String = _
  @BeanProperty
  var budget_item: String = _
  @BeanProperty
  var fact_expenses: JBigInt = _
  @BeanProperty
  var budget_total: JBigInt = _
  @BeanProperty
  var budget_q1: JBigInt = _
  @BeanProperty
  var budget_q2: JBigInt = _
  @BeanProperty
  var budget_q3: JBigInt = _
  @BeanProperty
  var budget_q4: JBigInt = _
  @OneToOne
  @JoinColumn(name = "approver_q1")
  @BeanProperty
  var approver_q1: User = _
  @OneToOne
  @JoinColumn(name = "approver_q2")
  @BeanProperty
  var approver_q2: User = _
  @OneToOne
  @JoinColumn(name = "approver_q3")
  @BeanProperty
  var approver_q3: User = _
  @OneToOne
  @JoinColumn(name = "approver_q4")
  @BeanProperty
  var approver_q4: User = _
  @BeanProperty
  var comment: String = _

  @BeanProperty
  @ManyToOne
  @JoinColumn(name = "pr_id")
  var project: Project = _

}

object Budget extends Dao(classOf[Budget]) {
}








