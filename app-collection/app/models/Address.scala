package models

import java.util.Date

import io.ebean.annotation.WhenCreated
import javax.persistence._

import scala.beans.BeanProperty

@Entity
@Table(name = "address")
class Address extends BaseEntity {
  @BeanProperty
  var addr_type: String = _
  @BeanProperty
  var mail_index: String = _
  @BeanProperty
  var addr_region: String = _
  @BeanProperty
  var addr_town: String = _
  @BeanProperty
  var addr_street: String = _
  @BeanProperty
  var addr_house: String = _
  @BeanProperty
  var addr_building: String = _
  @BeanProperty
  var addr_appartment: String = _
  @BeanProperty
  @WhenCreated
  var created: Date = _
  @OneToOne
  @JoinColumn(name = "author_id")
  @BeanProperty
  var author: User = _

  @BeanProperty
  @ManyToOne
  @JoinColumn(name = "legal_entity_id")
  var legalEntity: LegalEntity = _

  @BeanProperty
  @ManyToOne
  @JoinColumn(name = "individual_id")
  var individual: Individual = _
}

object Address extends Dao(classOf[Address]) {
}





