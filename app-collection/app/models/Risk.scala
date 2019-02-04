package models

import java.util.Date

import io.ebean.annotation.WhenCreated
import javax.persistence._

import scala.beans.BeanProperty

@Entity
@Table(name = "risk")
class Risk extends BaseEntity {
  @BeanProperty
  var risk_type: String = _
  @BeanProperty
  var rise_period: String = _
  @BeanProperty
  var rise_reason: String = _
  @BeanProperty
  var outcome_type: String = _
  @BeanProperty
  var work_date: Date = _

  @BeanProperty
  @ManyToOne
  @JoinColumn(name = "pr_id", nullable = false)
  var project: Project = _
}

object Risk extends Dao(classOf[Risk]) {
}








