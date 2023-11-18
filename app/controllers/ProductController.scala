package controllers

import com.google.inject.Inject
import controllers.actions.authAction
import models.{LoginService, ProductsService}
import models.dto._
import models.dto.Mappings._
import play.api.http.Writeable
import play.api.libs.json.{Json, Writes}
import play.api.mvc.{Action, AnyContent, Controller}

class ProductController @Inject()(
                                   loginService: LoginService,
                                   productsService: ProductsService) extends Controller{

//  def list() = authAction(loginService){ ur =>
//    Ok(views.html.products.list(List(
//      ProductDTO("1", "foo1", "bar1"),
//      ProductDTO("2", "foo2", "bar2"),
//      ProductDTO("3", "foo3", "bar3"),
//      ProductDTO("4", "foo4", "bar4")
//    )))
//  }
  implicit def writeable[T](implicit writes: Writes[T]): Writeable[T] =
    new Writeable[T](Writeable.writeableOf_JsValue.transform compose writes.writes, Some(JSON))

  def create(): Action[AnyContent] = Action { r =>
    val dto = r.body.asJson.map(x => Json.fromJson[ProductCreateDto](x)).get.get
    val created = productsService.create(dto)
    val result = ProductDTO(created.id, created.title, created.description)

    Created(result)
  }
  def update(id: String): Action[AnyContent] = Action { r =>
    val dto = r.body.asJson.map(x => Json.fromJson[ProductUpdateDto](x)).get.get
    val updated = productsService.update(id, dto)
    val result = ProductDTO(updated.id, updated.title, updated.description)

    Ok(result)
  }

  def delete(id: String): Action[AnyContent] = Action {
    val deleted = productsService.delete(id).map(x => ProductDTO(x.id, x.title, x.description))

    deleted match {
      case Some(x) => Ok(x)
      case None => NotFound
    }
  }

  def list(): Action[AnyContent] = Action {
    Ok(productsService.getAll().map(x => ProductDTO(x.id, x.title, x.description)))
  }

  def find(title: String): Action[AnyContent] = Action {
    val result = productsService.find(title)
      .map(x => ProductDTO(x.id, x.title, x.description))

    Ok(result)
  }
}
