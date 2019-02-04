package models

import io.ebean.text.json.JsonContext
import io.ebean.{Ebean, Query}

abstract class Dao[T](cls: Class[T]) {
  /**
    * Find by Id.
    */
  def find(id: Any): T = Ebean.find(cls, id)

  /**
    * Find with expressions and joins etc.
    */
  def find(): Query[T] = Ebean.find(cls)

  /**
    * Return a reference.
    */
  def ref(id: Any): T = Ebean.getReference(cls, id)

  /**
    * Save (insert or update).
    */
  def save(o: Any): Unit = Ebean.save(o)

  /**
    * insert (insert).
    */
  def insert(o: Any): Unit = Ebean.insert(o)

  /**
    * update (update).
    */
  def update(o: Any): Unit = Ebean.update(o)

  /**
    * Delete.
    */
  def delete(o: Any): Unit = Ebean.delete(o)

  def jsonContext: JsonContext = Ebean.json()
}
