package models.dao.repositories

import models.dao.repositories.ProductsRepositoryData.products
import models.Product

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
