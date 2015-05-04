package controllers

import actors.{SystemActor, WebSocketActor}
import akka.actor.Props
import akka.util.Timeout
import models.ChatRoom
import play.api.Play.current
import play.api.mvc.{Action, Controller, WebSocket}
import play.libs.Akka

import scala.concurrent.Future
import scala.concurrent.duration._

/**
 * Created by kasonchan on 5/3/15.
 */
object Chat extends Controller {

  def socket = WebSocket.acceptWithActor[String, String] { request =>
    out =>
      WebSocketActor.props(out)
  }

  implicit val timeout = Timeout(1.seconds)
  val room = Akka.system.actorOf(Props[ChatRoom])

  def chat(msg: String) = Action.async { implicit request =>
    SystemActor.log ! msg
    Future.successful(Ok(views.html.chat()(request)))
  }

  def chatSocket(msg: String): WebSocket[String, String] =
    WebSocket.acceptWithActor[String, String] { request =>
      out => WebSocketActor.props(out)
    }
}
