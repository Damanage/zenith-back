package models

import javax.persistence._

import scala.beans.BeanProperty

@MappedSuperclass
abstract class BaseEntity {
  type JLong = java.lang.Long
  type JInt = java.lang.Integer
  type JBigInt = java.math.BigDecimal
  type JList[A] = java.util.List[A]

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @BeanProperty
  @Column(name = "ID")
  var id: JLong = _
}
