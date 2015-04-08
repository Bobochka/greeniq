package controllers

import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.libs.concurrent.{Akka, Promise}
import play.api.libs.iteratee._
import play.api.Logger
import scala.concurrent.ExecutionContext.Implicits.global
import java.net.{InetAddress, UnknownHostException}
import scala.concurrent.Future

import models.Hub




object Hubs extends Controller {

  val Home = Redirect(routes.Hubs.index)

  def index = Action { implicit request =>
    Ok(views.html.index(Hub.all()))
  }

  def newHub = Action {
    Ok(views.html.newhub(hubSharedForm))
  }

  def createHub = Action { implicit request =>
    hubSharedForm.bindFromRequest.fold(
      formWithErrors => BadRequest(views.html.newhub(formWithErrors)),
      hub => {
        Hub.create(hub)
        Home.flashing("success" -> "Hub has been create successfully")
      }
    )
  }

  def editHub(id: Long) = Action {
    Hub.find(id).map { hub =>
      Ok(views.html.edithub(id, hubSharedForm.fill(hub)))
    }.getOrElse(NotFound)
  }

  def updateHub(id: Long) = Action { implicit request =>
    hubSharedForm.bindFromRequest.fold(
      formWithErrors => BadRequest(views.html.edithub(id, formWithErrors)),
      hub => {
        Hub.update(id, hub)
        Home.flashing("success" -> "Hub has been updated successfully")
      }
    )
  }


  def deleteHub(id: Long) = Action {
    Hub.delete(id)
    Home.flashing("success" -> "Hub has been deleted successfully")
  }

  def analyseHub(id: Long) = WebSocket.async[String] { implicit request =>
    Future {
      val in = Iteratee.consume[String]()

      Hub.find(id).map { hub =>
        var i = 0

        val out = Enumerator.generateM[String](Promise.timeout({
          i += 1

          if(i <= 10)
            Some(InetAddress.getByName(hub.ip).isReachable(1001).toString())
          else
            Some("How to return Enumerator.eof here? :(")
        }, 1000))

        (in, out)
      }.getOrElse((in, Enumerator("").andThen(Enumerator.eof)))
    }
  }

  val nonEmptyIP: Mapping[String] = nonEmptyText.verifying("Should be valid IP", value => value.matches("(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)") )

  val hubSharedForm = Form(
    mapping(
      "id" -> ignored(None:Option[Long]),
      "name" -> nonEmptyText,
      "description" -> nonEmptyText,
      "ip" -> nonEmptyIP
    )(Hub.apply)(Hub.unapply)
  )


}
