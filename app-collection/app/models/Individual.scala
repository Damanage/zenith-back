package models

import java.util.Date

import javax.persistence._

import scala.beans.BeanProperty

@Entity
@Table(name = "individual")
class Individual extends BaseEntity {
  @BeanProperty
  var person_name: String = _
  @BeanProperty
  var person_surname: String = _
  @BeanProperty
  var person_patrname: String = _
  @BeanProperty
  var person_role: String = _
  @BeanProperty
  var birth_date: Date = _
  @BeanProperty
  var person_passport: String = _
  @BeanProperty
  var person_status: String = _
  @BeanProperty
  var bankrupt_flg: JInt = _
  @BeanProperty
  var acc_arest_flg: JInt = _

  @BeanProperty
  @ManyToOne
  @JoinColumn(name = "pr_id", nullable = false)
  var project: Project = _

  @BeanProperty
  @OneToMany(mappedBy = "individual")
  var addresses: JList[Address] = _

  @BeanProperty
  @OneToMany(mappedBy = "individual")
  var telephones: JList[Telephone] = _

  @BeanProperty
  @OneToMany(mappedBy = "individual")
  var emails: JList[Email] = _
}

object Individual extends Dao(classOf[Individual]) {
}





