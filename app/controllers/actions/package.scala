package controllers

import controllers.Assets.Forbidden
import play.api.mvc.{ActionBuilder, ActionFilter, ActionTransformer, Request, WrappedRequest}

import scala.concurrent.Future

package object actions {
  class UserRequest[A](val email: Option[String], request: Request[A]) extends WrappedRequest[A](request)
  object UserAction extends ActionBuilder[UserRequest] with ActionTransformer[Request, UserRequest]{
    def transform[A](request: Request[A]) = Future.successful(
      new UserRequest(request.session.get("email"), request)
    )
  }

  object PermissionCheckAction extends ActionFilter[UserRequest]{
    private val validUserNames = Set("user@gmail.com")

    def filter[A](input: UserRequest[A]) = Future.successful(
      if(!validUserNames.contains(input.email.getOrElse("")))
        Some(Forbidden)
      else None
    )
  }

  def authAction: ActionBuilder[UserRequest] = UserAction andThen PermissionCheckAction
}
