package controllers

import controllers.actions.authAction
import models.dto.ProductDTO
import play.api.mvc.{Action, Controller}

class ProductController extends Controller{

  def list() = authAction{ ur =>
    Ok(views.html.products.list(List(
      ProductDTO("1", "foo1", "bar1"),
      ProductDTO("2", "foo2", "bar2"),
      ProductDTO("3", "foo3", "bar3"),
      ProductDTO("4", "foo4", "bar4")
    )))
  }
}
