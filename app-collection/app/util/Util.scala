package util

import java.nio.charset.StandardCharsets

object Util {
  def fromBytes(v: Array[Byte]) = new String(v, StandardCharsets.UTF_8)
  def toBytes(v: String): Array[Byte] = v.getBytes(StandardCharsets.UTF_8)
}
