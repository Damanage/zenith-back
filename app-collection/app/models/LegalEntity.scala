package models

import javax.persistence._

import scala.beans.BeanProperty

@Entity
@Table(name = "legal_entity")
class LegalEntity extends BaseEntity {
  @BeanProperty
  var legal_name: String = _
  @BeanProperty
  var legal_role: String = _
  @BeanProperty
  var legal_okved: String = _
  @BeanProperty
  var legal_opf: String = _

  @BeanProperty
  @ManyToOne
  @JoinColumn(name = "pr_id", nullable = false)
  var project: Project = _

  @BeanProperty
  @OneToMany(mappedBy = "legalEntity")
  var addresses: JList[Address] = _

  @BeanProperty
  @OneToMany(mappedBy = "legalEntity")
  var telephones: JList[Telephone] = _

  @BeanProperty
  @OneToMany(mappedBy = "legalEntity")
  var emails: JList[Email] = _
}

object LegalEntity extends Dao(classOf[LegalEntity]) {
}


