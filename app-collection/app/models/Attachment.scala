package models

import java.util.Date

import io.ebean.annotation.{JsonIgnore, WhenCreated}
import javax.persistence._

import scala.beans.BeanProperty

@Entity
@Table(name = "attachment")
class Attachment extends BaseEntity {
  @BeanProperty
  var full_name: String = _
  @BeanProperty
  var doc_type: String = _
  @BeanProperty
  var file_size: JInt = _
  @BeanProperty
  var file_type: String = _
  @BeanProperty
  var author_id: JLong = _
  @BeanProperty
  @WhenCreated
  var created: Date = _
  @BeanProperty
  @Basic(fetch=FetchType.LAZY)
  @Lob
  var file_body: Array[Byte] = _

  @OneToOne
  @JoinColumn(name = "author_id")
  @BeanProperty
  var author: User = _

  @BeanProperty
  @ManyToOne
  @JoinColumn(name="doc_id", nullable=false)
  var creditAgreement: CreditAgreement = _
}

object Attachment extends Dao(classOf[Attachment]) {
}
