import play.api.mvc.Results._
import play.api.mvc._
import play.api.{GlobalSettings, Logger}

import scala.concurrent.Future

/**
 * Created by kasonchan on 5/18/15.
 */
object Global extends GlobalSettings {

  /**
   * On handler not found
   * Handle 404 page not found error
   * @param request RequestHeader
   * @return Future[SimpleResult]
   */
  override def onHandlerNotFound(request: RequestHeader) = {
    request.session.get("username").map { username =>
      Logger.info("Global.onHandlerNotFound - " + username)
      Future.successful(NotFound(views.html.global.notFound(Some(username))(Some(request.path))))
    }.getOrElse {
      Logger.info("Global.onHandlerNotFound")
      Future.successful(NotFound(views.html.global.notFound(None)(Some(request.path))))
    }
  }

  /**
   * On Error
   * Handle 500 internal server error
   * @param request RequestHeader
   * @param throwable Throwable
   * @return Future[SimpleResult]
   */
  override def onError(request: RequestHeader, throwable: Throwable) = {
    request.session.get("username").map { username =>
      Logger.error("Global.onError - " + request.toString() + " - " + throwable.toString)
      Future.successful(InternalServerError(views.html.global.onError(Some(username))(Some(throwable))))
    }.getOrElse {
      Logger.error("Global.onError - " + request.toString() + " - " + throwable.toString)
      Future.successful(InternalServerError(views.html.global.onError(None)(Some(throwable))))
    }
  }

}
