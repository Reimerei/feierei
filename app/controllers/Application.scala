package controllers

import play.api._
import play.api.mvc._

object Application extends Controller {

  def index = Action {
    Ok(views.html.index())
  }

  def hinkommen() = Action {
    Ok(views.html.hinkommen())
  }
  def uebernachten() = Action {
    Ok(views.html.uebernachten())
  }
}