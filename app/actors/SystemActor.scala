package actors

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

/**
 * Created by kasonchan on 5/3/15.
 */
class SystemActor extends Actor with akka.actor.ActorLogging {

  def receive = {
    case m: String => log.info(m)
  }

}

object SystemActor {
  //    Create actor system
  val system: ActorSystem = ActorSystem("system")

  val log: ActorRef = system.actorOf(Props[SystemActor], name = "log")
}
