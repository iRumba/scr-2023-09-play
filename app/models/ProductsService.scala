package models

import com.google.inject.Inject
import models.dao.repositories.ProductsRepository
import models.dto.{ProductCreateDto, ProductUpdateDto}

import java.util.UUID

trait ProductsService {
  def create(product: ProductCreateDto): Product

  def update(id: String, productUpdateDto: ProductUpdateDto): Product

  def delete(id: String): Option[Product]

  def find(title: String): Traversable[Product]

  def getAll(): Traversable[Product]
}

class ProductsServiceImpl @Inject()(productsRepository: ProductsRepository) extends ProductsService {

  override def create(product: ProductCreateDto): Product = {
    val entity = Product(
      id = UUID.randomUUID().toString,
      title = product.title,
      description = product.description
    )

    productsRepository.add(entity)
  }

  override def update(id: String, productUpdateDto: ProductUpdateDto): Product = {
    productsRepository.update(Product(
      id,
      productUpdateDto.title,
      productUpdateDto.description))
  }

  override def delete(id: String): Option[Product] = productsRepository.delete(id)

  override def find(title: String): Traversable[Product] = productsRepository.find(title)

  override def getAll(): Traversable[Product] = productsRepository.getAll()
}