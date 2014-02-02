package controllers


import play.api.mvc._
import play.api.data.Forms._
import play.api.data._
import play.api.Logger
import java.util.Date
import play.api.libs.json.{Json, Format}
import play.modules.reactivemongo.json.collection.JSONCollection
import play.modules.reactivemongo.ReactiveMongoPlugin
import play.api.Play.current
import scala.concurrent.ExecutionContext
import ExecutionContext.Implicits.global

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

  def anmelden() = Action {
    Ok(views.html.anmelden())
  }

  def anmeldenSubmit() = Action {

    request =>

      Logger.debug("Request: " + request.body)

      Anmeldung.form.bindFromRequest()(request).fold(
        errors => {
          BadRequest("")
        },
        anmeldung => {

          Anmeldung.col.insert(anmeldung)
          Ok(views.html.anmeldenDanke())
        }
      )


  }

  case class Anmeldung(
                        name: String,
                        numberOrPeople: String,
                        sleep: String,
                        mfgNeed: Option[Boolean],
                        mfgWant: Option[Boolean],
                        notes: String,
                        submitted: Date
                        )

  object Anmeldung {

    def col: JSONCollection = ReactiveMongoPlugin.db.collection[JSONCollection]("anmeldungen")

    val form = Form(
      mapping(
        "name" -> text,
        "numberOfPeople" -> text,
        "sleep" -> text,
        "mfgNeed" -> optional(boolean),
        "mfgWant" -> optional(boolean),
        "notes" -> text,
        "date" -> ignored(new Date)
      )(Anmeldung.apply)(Anmeldung.unapply)
    )

    implicit val mongoFormat: Format[Anmeldung] = Json.format[Anmeldung]

  }


}