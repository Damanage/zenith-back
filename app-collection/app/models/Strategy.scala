package models

import java.util.Date

import io.ebean.annotation.WhenCreated
import javax.persistence._

import scala.beans.BeanProperty

@Entity
@Table(name = "strategy")
class Strategy extends BaseEntity {
  @BeanProperty
  var strategy_name: String = _
  @BeanProperty
  var strategy_type: String = _
  @BeanProperty
  var strategy_date: Date = _
  @BeanProperty
  var strategy_close_date: Date = _
  @OneToOne
  @JoinColumn(name = "author_id")
  @BeanProperty
  var author: User = _
  @BeanProperty
  @WhenCreated
  var created: Date = _
  @BeanProperty
  var description: String = _
  @OneToOne
  @JoinColumn(name = "approved_id")
  @BeanProperty
  var approver: User = _
  @BeanProperty
  var approved_date: Date = _
  @BeanProperty
  var approved_group_date: Date = _
  @BeanProperty
  var approved_kpa_date: Date = _
  @BeanProperty
  var kpa_number: String = _
  @BeanProperty
  var strategy_budget: JBigInt = _

  @BeanProperty
  @ManyToOne
  @JoinColumn(name = "pr_id")
  var project: Project = _
}

object Strategy extends Dao(classOf[Strategy]) {
}










