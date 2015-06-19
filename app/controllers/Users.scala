package controllers

import models.User
import play.api.Play.current
import play.api.libs.json.Json
import play.api.libs.ws.{WS, WSAuthScheme}
import play.api.mvc.{Action, AnyContent, Controller}

import scala.concurrent.ExecutionContext.Implicits.global
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
    try {
      def username = request.body.asFormUrlEncoded.get("username")(0)
      def password = request.body.asFormUrlEncoded.get("password")(0)

      val user = User(Some(username), None, Some(password))
      println(user)

      // TODO: Finish login
      WS.url("http://45.55.86.67:9000/api/v0.1/user/rooms")
        .withHeaders(("Content-Type", "application/json; charset=utf-8"))
        .withAuth("", "", WSAuthScheme.BASIC)
        .get().map { response =>
        response.status match {
          case 200 =>
            Ok(response.body.toString)
          case 404 =>
            NotFound(response.body.toString)
          case _ =>
            Status(response.status)(response.body.toString)
        }
      }
    } catch {
      case e: Exception =>
        val response = Json.obj("messages" -> Json.arr("Bad credentials"))
        Future.successful(Unauthorized(response.toString()))
    }

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
    println(user.toString)

    val userJson = Json.obj(
      "login" -> username,
      "email" -> email,
      "password" -> password
    )

    // TODO: Finish signup
    WS.url("http://45.55.86.67:9000/api/v0.1/users")
      .withHeaders(("Content-Type", "application/json; charset=utf-8"))
      .post(userJson).map { response =>
      response.status match {
        case 200 =>
          Ok(response.body.toString)
        case 404 =>
          NotFound(response.body.toString)
        case 400 =>
          BadRequest(response.body.toString)
      }
    }
  }

}
