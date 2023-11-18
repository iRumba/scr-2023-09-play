package models.dto

import play.api.libs.json.Json

case class ProductDTO(id: String, title: String, description: String)

case class ProductCreateDto(title: String, description: String)

case class ProductUpdateDto(title: String, description: String)

object Mappings {
  implicit val productDto = Json.format[ProductDTO]
  implicit val productCreationDto = Json.format[ProductCreateDto]
  implicit val productEditingDto = Json.format[ProductUpdateDto]
}