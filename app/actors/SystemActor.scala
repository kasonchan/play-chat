package actors

import akka.actor._
import models.{ChatRoom, Join}
import play.api.Play.current
import play.api.libs.concurrent.Akka


/**
 * Created by kasonchan on 5/3/15.
 */
class SystemActor extends Actor with ActorLogging {

  override def preStart() = {
    log.info("Prestart")
  }

  override def postStop() = {
    log.info("Poststop")
  }

  def receive = {
    case s: String =>
      log.info(s)
    case j: Join =>
      log.info(j.toString)
    case x =>
      log.error(x.toString)
  }

}

object SystemActor {

  // Create actor system
  val system: ActorSystem = ActorSystem("system")

  // Actor for logging
  val log: ActorRef = system.actorOf(Props[SystemActor], name = "log")

  // Actor for chat room
  val chatRoom: ActorRef = system.actorOf(Props[ChatRoom], name = "chatRoom")

  // Application actor system actor aLog
  val aLog: ActorRef = Akka.system.actorOf(Props[SystemActor], name = "log")

}
