package models.dao.repositories

import models.dao.repositories.ProductsRepositoryData.products
import models.Product
import models.dao.schema.ProductRecordsSlickSchema
import slick.lifted.TableQuery

import scala.concurrent.{Await, Future}
import scala.concurrent.duration.DurationInt
import slick.dbio.Effect
import slick.lifted.TableQuery
import slick.jdbc.PostgresProfile.api._
import slick.sql.SqlAction
import db.SlickDatabase

import scala.language.postfixOps

trait ProductsRepository {
  def getById(id: String): Option[Product]
  def add(product: Product): Product
  def update(product: Product): Product
  def delete(id: String): Option[Product]
  def find(title: String): Traversable[Product]

  def getAll(): Traversable[Product]
}

class ProductsRepositoryInMemoryImpl extends ProductsRepository {
  override def getById(id: String): Option[Product] = products.get(id)

  override def add(product: Product): Product = {
    products.put(product.id, product) match {
      case Some(_) => throw new Throwable("Already exists")
      case None => product
    }
  }

  override def update(product: Product): Product = {
    if (products.contains(product.id)) {
      products.update(product.id, product)
      product
    } else
      throw new Throwable("Not exists")
  }

  override def delete(id: String): Option[Product] = products.remove(id)

  override def find(title: String): Traversable[Product] = products.filter { case (_, p) =>
    p.title contains title
  }.values

  override def getAll(): Traversable[Product] = products.values
}

private object ProductsRepositoryData {
  val products: scala.collection.mutable.Map[String, Product] =
    scala.collection.mutable.Map[String, Product]()

}

class ProductsRepositorySlickImpl extends ProductsRepository with SlickDatabase{
  private val productsRecord = TableQuery[ProductRecordsSlickSchema]

  def t = {
    val f: Future[Int] = ???
    val r = Await.result(f, 1 second)
  }
  override def getById(id: String): Option[Product] = {
    val query = productsRecord.filter(_.id === id).result.headOption
    val result = db.run(query)
    Await.result(result, 1 second)
  }

  override def add(product: Product): Product = {
    val query = productsRecord += product
    val f = db.run(query)
    Await.result(f, 1 second)
    product
  }

  override def update(product: Product): Product = {
    val query = productsRecord.filter(_.id === product.id).update(product)
    val f = db.run(query)
    Await.result(f, 1 second)
    product
  }

  override def delete(id: String): Option[Product] = {
    val q1 = productsRecord.filter(_.id === id).result.headOption
    val resF = db.run(q1)
    val result = Await.result(resF, 1 second)
    val query = productsRecord.filter(_.id === id).delete
    val f = db.run(query)
    Await.result(f, 1 second)
    result
  }

  override def find(title: String): Traversable[Product] = {
    val query = productsRecord.filter(_.title like s"%$title%").result
    val result = db.run(query)
    Await.result(result, 1 second)
  }

  override def getAll(): Traversable[Product] = {
    val query = productsRecord.result
    val result = db.run(query)
    Await.result(result, 1 second)
  }
}