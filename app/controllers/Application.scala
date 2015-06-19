package controllers

import models.User
import play.api.libs.iteratee.{Enumerator, Iteratee}
import play.api.mvc.{Action, AnyContent, Controller}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

object Application extends Controller {

  val concatIteratee = Iteratee.fold("") {
    (cons: String, chunk: String) => cons.concat(chunk)
  }

  /**
   * Greeting
   * Generate sequence of greetings - Hello World!
   * @return Seq[String]
   */
  def greeting: Seq[String] = {
    // Create enumerator of Hello World
    val enumerator: Enumerator[String] =
      Enumerator("Hello", " World!").andThen(Enumerator.eof)

    // Create iteratee future iterate enumerator
    val iterateeFuture: Future[Iteratee[String, String]] =
      enumerator(concatIteratee)

    // Iterate the enumerator
    val resultFuture: Future[String] = enumerator run concatIteratee

    // Wait for the result for 3 seconds
    val result = Await.result(resultFuture, 3.seconds)

    Seq(result)
  }

  /**
   * Login
   * Route to login view
   * @return
   */
  def login: Action[AnyContent] = Action.async { implicit request =>

    val greets: Seq[String] = greeting

    // Print the greeting result to the login screen
    Future.successful(Ok(views.html.login(greets)(User(None, None, None))(Seq())))
  }

  /**
   * Signup
   * Route to signup view
   * @return Action[AnyContent]
   */
  def signup: Action[AnyContent] = Action.async { implicit request =>

    val greets: Seq[String] = greeting

    // Print the greeting result to the signup screen
    Future.successful(Ok(views.html.signup(greets)(User(None, None, None))(Seq())))
  }

}