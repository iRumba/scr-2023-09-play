package models

import play.api.libs.functional.syntax.{functionalCanBuildApplicative, toFunctionalBuilderOps, unlift}
import play.api.libs.json.{JsPath, JsValue, Json, Reads, Writes}
import play.api.mvc.PathBindable

case class User(id: UserId, email: Email, password: String)

object User{
//  implicit val reads: Reads[User] = (
//    (JsPath \ "id").read[UserId] and
//      (JsPath \ "email").read[Email]
//  )(User.apply _)

  implicit val reads = Json.reads[User]

//  implicit val writes: Writes[User] = (
//    (JsPath \ "id").write[UserId] and
//      (JsPath \ "email").write[Email]
//  )(unlift(User.unapply))
  implicit val writes = Json.writes[User]
}

case class UserId(raw: Long)

object UserId{
   implicit def pathBindable(implicit longBindable: PathBindable[Long]) = new PathBindable[UserId] {
     override def bind(key: String, value: String): Either[String, UserId] =
       longBindable.bind(key, value).right.map(UserId(_))

     override def unbind(key: String, value: UserId): String =
       longBindable.unbind(key, value.raw)
   }

//  implicit val reads: Reads[UserId] = (JsPath \ "raw").read[Long].map(UserId(_))
//  implicit val writes: Writes[UserId] = new Writes[UserId] {
//    override def writes(o: UserId): JsValue = Json.obj(
//      "raw" -> o.raw
//    )
//  }
  implicit val reads: Reads[UserId] = Json.reads[UserId]
  implicit val writes: Writes[UserId] = Json.writes[UserId]
}

case class Email(raw: String)

object Email{
//  implicit val reads: Reads[Email] = (JsPath \ "raw").read[String]
//    .map(Email(_))
//
//  implicit val writes: Writes[Email] = new Writes[Email] {
//    override def writes(o: Email): JsValue = Json.obj(
//      "raw" -> o.raw
//    )
//  }

  implicit val reads: Reads[Email] = Json.reads[Email]
  implicit val writes: Writes[Email] = Json.writes[Email]
}