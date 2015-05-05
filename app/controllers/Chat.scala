package controllers

import actors.{SystemActor, WebSocketActor}
import akka.pattern.ask
import akka.util.Timeout
import models.Join
import play.api.Play.current
import play.api.libs.iteratee.{Enumerator, Iteratee}
import play.api.mvc.{Action, Controller, WebSocket}

import scala.concurrent.Future
import scala.concurrent.duration._

/**
 * Created by kasonchan on 5/3/15.
 */
object Chat extends Controller {

  implicit val timeout = Timeout(1.seconds)

  def chat(user: String) = Action.async { implicit request =>

    // Log the user
    SystemActor.log ! user

    // Show the chatroom screen
    Future.successful(Ok(views.html.chat(user)(request)))
  }

  def chatSocket(user: String) = WebSocket.async { request =>

    // Ask for result
    val channelsFuture = SystemActor.chatRoom ? Join(user)

    channelsFuture.mapTo[(Iteratee[String, _], Enumerator[String])]
  }

  //  def chatSocket(user: String) = WebSocket.acceptWithActor[String, String] { request =>
  //    out =>
  //      val channelsFuture = SystemActor.chatRoom ? Join(user)
  //
  //      channelsFuture.mapTo[(Iteratee[String, _], Enumerator[String])]
  //
  //      WebSocketActor.props(out)
  //  }

}
