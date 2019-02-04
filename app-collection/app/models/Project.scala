package models

import java.util.Date

import javax.persistence._

import scala.beans.BeanProperty

@Entity
@Table(name = "project")
class Project extends BaseEntity {
  @BeanProperty
  var project_name: String = _
  @BeanProperty
  var project_type: String = _
  @BeanProperty
  var project_status: String = _
  @BeanProperty
  var date_open: Date = _
  @BeanProperty
  var date_close: Date = _
  @OneToOne
  @JoinColumn(name = "curator_id")
  @BeanProperty
  var curator: User = _
  @OneToOne
  @JoinColumn(name = "manager_id")
  @BeanProperty
  var manager: User = _
  @BeanProperty
  var debt: JBigInt = _
  @BeanProperty
  var balance_holder: String = _
  @BeanProperty
  var problem_status: String = _
  @BeanProperty
  var problem_reason: String = _
  @BeanProperty
  var fact_expenses: JBigInt = _
  @BeanProperty
  var current_debt: JBigInt = _

  @BeanProperty
  @OneToMany(mappedBy = "project")
  var risks: JList[Risk] = _
  @BeanProperty
  @OneToMany(mappedBy = "project")
  var tasks: JList[Task] = _
  @BeanProperty
  @OneToMany(mappedBy = "project")
  var strategies: JList[Strategy] = _
  @BeanProperty
  @OneToMany(mappedBy = "project")
  var budget: JList[Budget] = _


}

object Project extends Dao(classOf[Project]) {
}
