package controllers

import models.{Paging, User, UserId}
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, Controller, Cookie, DiscardingCookie}

case class Foo(foo: String)



object IndexController extends Controller{

  def foo(p : Option[Int] = Some(2)): Option[Int] = ???

  // f: Request => Result

  def action1(): Action[AnyContent] = Action{
    Ok
  }

  def action2() = Action{
    Ok("Hello world")
  }

  def action3() = Action{ rc =>
    Ok(s"Action 3 $rc")
  }

  def action4() = Action{ rc =>
    NotFound
  }

  def action5() = Action{ rc =>
    BadRequest
  }

  def action6() = Action{ rc =>
    InternalServerError("ooops")
  }

  def action7() = Action{ rc =>
    Status(488)("Hello 488")
  }

  def action8() = Action{ rc =>
   Redirect(routes.IndexController.action2())
  }

  def action9(id: Int) = Action{
    Ok(s"Hello $id")
  }

  def action10() = Action{
    val textPlain = Ok("<h1>Hello world</h1>")
    val html = Ok("<h1>Hello world</h1>").as(HTML)
    val json = Ok("{'foo': 'Hello world'}").as(JSON)
    json
  }


  // Продолжаем работать с результатом

  def action11() = Action{
    Ok("Hello world")
      .withHeaders(CACHE_CONTROL -> "max-age=3600", ETAG -> "xx")
  }


  // Установка заголовков + cookie

  def action12() = Action{
    Ok("Hello world")
      .discardingCookies(DiscardingCookie("user"))
  }

  //сессия

  def action13 = Action{
    Ok("Hello session").withSession(
      "connected" -> "user@gmail.com"
    )
  }

  def action14 = Action{ rc =>
    rc.session.get("connected").map{ email =>
      Ok("Hello, " + email)
    }.getOrElse{
      Unauthorized("Ooops you are not connected")
    }

//    Ok("Hello session").withSession(rc.session + ("said Hello" -> "yes"))
//    Ok("Hello session").withSession(rc.session - "said Hello")
  }


  // typed params

  def action15(userId: UserId) = Action{
    Ok(userId.toString)
  }

  def action16(paging: Paging) = Action{
    Ok(paging.toString)
  }


  // body parsing

  def action17 = Action{ rc =>
    val body: AnyContent = rc.body
    val textBody: Option[String] = body.asText
    textBody.map{ txt =>
      Ok(txt)
    }.getOrElse(BadRequest("text plain expected"))
  }

  def action18 = Action(parse.text){ rc =>
    Ok(rc.body)
  }

  // json

  def action19 = Action(parse.json[User]){rc =>
    val user: User = rc.body
    Ok(Json.toJson(user))
  }



}
