package models

import java.util.Date

import io.ebean.annotation.WhenCreated
import javax.persistence._

import scala.beans.BeanProperty

@Entity
@Table(name = "task")
class Task extends BaseEntity {
  @BeanProperty
  var task_name: String = _
  @BeanProperty
  var planned_close_date: Date = _
  @BeanProperty
  var close_date: Date = _
  @OneToOne
  @JoinColumn(name = "resp_id")
  @BeanProperty
  var responsible: User = _
  @BeanProperty
  var risk_status: String = _
  @BeanProperty
  var risk_priority: String = _
  @BeanProperty
  var comment: String = _
  @BeanProperty
  @WhenCreated
  var created: Date = _
  @OneToOne
  @JoinColumn(name = "author_id")
  @BeanProperty
  var author: User = _

  @BeanProperty
  @ManyToOne
  @JoinColumn(name = "pr_id")
  var project: Project = _
}

object Task extends Dao(classOf[Task]) {
}










