package controllers

import actors.SystemActor
import play.api.mvc.{Action, Controller}

import scala.concurrent.Future

object Application extends Controller {

  def index = Action.async {
    SystemActor.log ! "Hello World!"
    Future.successful(Ok(views.html.index()))
  }

}