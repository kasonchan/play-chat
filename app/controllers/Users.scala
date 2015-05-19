package controllers

import models.User
import play.api.mvc.{Action, AnyContent, Controller}

import scala.concurrent.Future

/**
 * Created by kasonchan on 5/18/15.
 */
object Users extends Controller {

  /**
   * Login
   * @return Action[AnyContent]
   */
  def login: Action[AnyContent] = Action.async { implicit request =>

    // Get username, email and password from the form
    def username = request.body.asFormUrlEncoded.get("username")(0)
    def email = request.body.asFormUrlEncoded.get("email")(0)
    def password = request.body.asFormUrlEncoded.get("password")(0)

    val user = User(Some(username), Some(email), Some(password))

    // TODO: Finish login

    Future.successful(Ok(views.html.chats(user)))
  }

  /**
   * Signup
   * @return Action[AnyContent]
   */
  def signup: Action[AnyContent] = Action.async { implicit request =>

    // Get username, email and password from the form
    def username = request.body.asFormUrlEncoded.get("username")(0)
    def email = request.body.asFormUrlEncoded.get("email")(0)
    def password = request.body.asFormUrlEncoded.get("password")(0)

    val user = User(Some(username), Some(email), Some(password))

    // TODO: Finish signup

    Future.successful(Ok(views.html.chats(user)))
  }

}
