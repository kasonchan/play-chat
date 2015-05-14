package controllers

import actors.SystemActor
import akka.pattern.ask
import akka.util.Timeout
import models.Join
import play.api.libs.iteratee.{Enumerator, Iteratee}
import play.api.mvc.{Action, Controller, WebSocket}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration._

/**
 * Created by kasonchan on 5/3/15.
 */
object Chat extends Controller {

  implicit val timeout = Timeout(1.seconds)

  /**
   * Chat function
   * @param user String: the name of the user
   * @return the future of chatroom screen
   */
  def chat(user: String) = Action.async { implicit request =>
    // Log the user
    SystemActor.log ! user

    // Show the chatroom screen
    Future.successful(Ok(views.html.chat(user)(request)))
  }

  /**
   * Chat socket
   * @param user String: the name of the user
   * @return the channel of iteratee and enumerator
   */
  def chatSocket(user: String): WebSocket[String, String] =
    WebSocket.tryAccept[String] { request =>
      // Send the received msg to the chatroom
      val channelsFuture: Future[(Iteratee[String, _], Enumerator[String])] =
        ask(SystemActor.chatRoom, Join(user))
          .mapTo[(Iteratee[String, _], Enumerator[String])]

      channelsFuture.map { cf =>
        Right(cf)
      }
    }
  
  /**
   * User function
   * @return the redirection to chatroom
   */
  def user = Action.async { implicit request =>
    val username: String = request.body.asFormUrlEncoded.get("username")(0)

    SystemActor.log ! username

    // Redirect to the chat function
    Future.successful(Redirect("/chat/" + username))
  }

}
