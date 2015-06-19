package models

import actors.SystemActor
import akka.actor.{Actor, ActorRef, Props}
import play.api.libs.iteratee.{Concurrent, Enumerator, Iteratee}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 * Created by kasonchan on 5/19/15.
 */
object MyWebSocketActor {
  def props(out: ActorRef) = Props(new MyWebSocketActor(out))
}

class MyWebSocketActor(out: ActorRef) extends Actor {
  def receive = {
    case msg: String =>
      out ! ("I received your message: " + msg)
  }
}

class TestRoom extends Actor {

  // A set of users' list
  var users = Set[String]()

  // Enumerator channel
  val (enumerator, channel) = Concurrent.broadcast[String]

  def receive = {

    case Join(user: String) => {
      // Log the join user
      SystemActor.log ! ("%s: %s" format(self.path, Join(user)))

      if (!users.contains(user)) {
        val iteratee = Iteratee.foreach[String] { msg =>
          self ! Broadcast("%s: %s" format(user, msg))
        }.mapM { _ =>
          Future {
            self ! Leave(user)
          }
        }

        // Add new user to the users list
        users += user

        // Send the message to channel
        channel.push("%s has joined the room, now the chatroom has %s users"
          format(user, users.size))

        sender() !(iteratee, enumerator)
      } else {
        val enumerator = Enumerator("%s has already joined the room" format user)

        // Ignore user messages
        val iteratee = Iteratee.ignore

        // Return the iteratee and enumerator to action
        sender() !(iteratee, enumerator)
      }
    }
    case Leave(user) => {
      // Log the leave user
      SystemActor.log ! ("%s: %s" format(self.path, Leave(user)))

      // Remove user from the users list
      users -= user

      // Add the msg to the channel
      channel.push("%s has left the room, %s users left"
        format(user, users.size))
    }
    case Broadcast(msg: String) => {
      // Log the msg
      SystemActor.log ! ("%s: %s" format(self.path, Broadcast(msg)))

      // Add the msg to channel
      channel.push(msg)
    }
    case x => {
      // Log error
      SystemActor.log ! ("%s: %s" format(self.path, x))
    }
  }

}
