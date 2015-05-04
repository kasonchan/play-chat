package models

import akka.actor.Actor

/**
 * Created by kasonchan on 5/3/15.
 */
case class Join(person: String)

class ChatRoom extends Actor {

  def receive = {
    case x => {
    }
  }

}

