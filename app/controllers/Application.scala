package controllers

import actors.SystemActor
import play.api.libs.iteratee.{Enumerator, Iteratee}
import play.api.mvc.{Action, AnyContent, Controller}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

object Application extends Controller {

  val concatIteratee = Iteratee.fold("") {
    (cons: String, chunk: String) => cons.concat(chunk)
  }

  def index: Action[AnyContent] = Action.async {
    SystemActor.log ! "Index"

    // Create enumerator of Hello World
    val enumerator: Enumerator[String] =
      Enumerator("Hello", " World").andThen(Enumerator.eof)

    // Create iteratee future iterate enumerator
    val iterateeFuture: Future[Iteratee[String, String]] =
      enumerator(concatIteratee)

    // Iterate the enumerator
    val resultFuture: Future[String] = enumerator run concatIteratee

    // Wait for the result for 3 seconds
    val result = Await.result(resultFuture, 3.seconds)

    // Print the result to the screen
    Future.successful(Ok(views.html.index(Some(result))))
  }

}