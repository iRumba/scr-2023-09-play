package models.dao.schema


import models.Product
import slick.lifted.{ProvenShape, Tag}
import slick.jdbc.PostgresProfile.api._

class ProductRecordsSlickSchema(tag: Tag) extends Table[Product](tag, "Products") {
  def id = column[String]("id", O.PrimaryKey)

  def title = column[String]("title")

  def description = column[String]("description")

  override def * : ProvenShape[Product] =  (id, title, description) <> (Product.tupled, Product.unapply)
}
