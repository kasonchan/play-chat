package actors

import akka.actor.{ActorLogging, Actor, ActorRef, Props}

/**
 * Created by kasonchan on 5/3/15.
 */
object WebSocketActor {

  def props(out: ActorRef) = Props(new WebSocketActor(out))

}

class WebSocketActor(out: ActorRef) extends Actor {

  override def preStart() = {
    SystemActor.log ! ("%s prestart" format self.path)
  }

  override def postStop() = {
    SystemActor.log ! ("%s prestart" format self.path)
  }

  def receive = {
    case msg: String =>
      out ! ("I received your message: " + msg)
  }

}
