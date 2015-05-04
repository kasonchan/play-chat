package actors

import akka.actor.{Actor, ActorRef, Props}

/**
 * Created by kasonchan on 5/3/15.
 */
object WebSocketActor {
  def props(out: ActorRef) = Props(new WebSocketActor(out))
}

class WebSocketActor(out: ActorRef) extends Actor {
  def receive = {
    case msg: String =>
      out ! ("I received your message: " + msg)
  }
}
