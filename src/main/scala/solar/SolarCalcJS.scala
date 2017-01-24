package solar

import org.scalajs.dom
//import org.scalajs.dom.html

import org.scalajs.dom._
// import scalajs.js.annotation._
import scalatags.JsDom.all._
//
// import scalatags.JsDom.svgTags._
// import scalatags.JsDom.{svgAttrs => svga}
//
import scala.scalajs.js.JSApp
//
// import scala.scalajs.js
//
// import dom.ext._
//
// import scalajs.concurrent.JSExecutionContext.Implicits.queue

//import scala.scalajs.js.Dynamic.{global => g}

object SolarCalcJS extends JSApp {
  def main(): Unit = {
  }

    val jsDiv = document.getElementById("js-div")

    var data = SolarData(1000)

    var cfls = 0

    var bulbs = 0

    val ledSpan = span(`class` := "pull-right bg-info")("0").render

    def ledUpdate() = {
      ledSpan.textContent = Led.savings(bulbs, cfls).toString()
    }

    import dom.window.localStorage

    case class QnBtn(qn: String, cls: String, txt: String){
      val btn = input(`class` := s"btn btn-${cls}")(`type`:="button", value := txt).render

      val key = s"qn-$txt-$qn"

      btn.onclick =
        (e: dom.Event) => {
          val present = scala.util.Try(localStorage.getItem(key).toInt).getOrElse(0)
          localStorage.setItem(key, (present + 1).toString)
        }

      btn.ondblclick =
        (e: dom.Event) =>
          {
            val present = scala.util.Try(localStorage.getItem(key).toInt).getOrElse(0)
          localStorage.setItem(key, (present - 2).toString)

          dom.window.alert(s"$key:" + localStorage.getItem(key))

        }
    }


    val cflsBox = input(`class` := "pull-right")(`type`:= "text", size := "3", value := 0).render

    val bulbsBox = input(`class` := "pull-right")(`type`:= "text", size := "3", value := 0).render

    import scalatags.JsDom.implicits._

    val areaBox = input(`class` := "pull-right")(`type`:= "text", size := "5", placeholder:= data.area).render

    val consBox = input(`class` := "pull-right")(`type`:= "text", size := "5", value := data.consumption).render

    // val effBox = input(`type`:= "text", size := "5", value := 135).render

    val inpDiv =
      div(
        div(),
        table(`class` := "table table-striped")(
          // caption("Enter your household data"),
          tbody(
          tr(td(" Enter your roof area (in square feet) : "), td(areaBox)),
          tr(td("Monthly consumption (in units): "), td(consBox))
          // tr(td("Efficiency (standard is 135): "), td(effBox))
        )
      )//,
        // div("House area:", span(" "), areaBox, span(" square feet")),
        // div("Efficiency:", span(" "), effBox, span(" (standard: 135)"))
      ).render

    val resDiv = div(
      table(`class` := "table table-striped")(
        caption("Your gains if you install Rooftop Solar"),
      tbody(
        tr(
          td(span("Monthly revenue from selling surplus electricity : ")),
          td(span(`class`:= "pull-right bg-info")("") )),
        tr(
          td(span("Monthly savings on electric bill : ")),
          td(span(`class`:= "pull-right bg-info")(""))),
        tr(
          td(span("Total gains over 25 years : ")),
          td(span(`class`:= "pull-right bg-info")("")))
      )
    ),
    table(`class` := "table table-bordered")(
      tbody(
        tr(
          td(span("One time cost of installation: ")),
          td(span(`class`:= "pull-right bg-info")("")))
)
)


    ).render

    val roofTopDiv =
      div(`class` := "col-md-5")(
        h4("Rooftop Solar"),
      inpDiv, resDiv).render

    val ledDiv =
      div(`class` := "col-md-offset-1 col-md-5")(
        h4("LED lighting"),
        table(`class` := "table table-striped")(
          // caption("Lights in your home"),
          tbody(
          tr(td("Enter number of incandescent bulbs in your home: "), td(bulbsBox)),
          tr(td("Enter number of CFLs in your home "), td(cflsBox))
        )),
        table(`class` := "table table-striped")(
          caption("Savings from switching to LEDs"),
          tbody(
          tr(td("Total savings over 20 years: "), td(ledSpan, "Rs"))
        )
      ),
        table(`class` := "table table-bordered")(
          caption("Your choices"),
          tbody(
            tr(
              td("Will you install rooftop solar?"),
              td(
                QnBtn("solar", "success", "yes").btn,
                span(" "),
                QnBtn("solar", "warning", "maybe").btn,
                span(" "),
                QnBtn("solar", "danger", "no").btn
              )
              ),
            tr(td("Will you switch to LEDs?"),
            td(
              QnBtn("led", "success", "yes").btn,
              span(" "),
              QnBtn("led", "warning", "maybe").btn,
              span(" "),
              QnBtn("led", "danger", "no").btn
            )
          )
          )
        )

      )

    val viewDiv = div(`class` := "row")(roofTopDiv, ledDiv).render

    jsDiv.appendChild(viewDiv)

    def showResult() = {
      resDiv.innerHTML = ""
      resDiv.appendChild(
        div(
          table(`class` := "table table-striped")(
            caption("Your gains if you install Rooftop Solar"),
          tbody(
            tr(
              td(span("Monthly revenue from selling surplus electricity : ")),
              td(span(`class`:= "pull-right bg-info")(s"${data.revenue.toInt}"), "Rs")),
            tr(
              td(span("Monthly savings on electric bill : ")),
              td(span(`class`:= "pull-right bg-info")(s"${data.savings.toInt}"), "Rs")),
            tr(
              td(span("Total gains over 25 years : ")),
              td(span(`class`:= "pull-right bg-info")(s"${(data.gains * 12 * 25).toInt}"), "Rs"))
            )
          ),
          table(`class` := "table table-bordered")(
          tbody(
            tr(
            td(span("One time cost of installation: ")),
            td(span(`class`:= "pull-right bg-info")(s"${data.cost.toInt}"), "Rs"))
          )
        )
        ).render
      )
    }

    areaBox.onchange =
      (event : dom.Event) => {
        data = data.copy(area = areaBox.value.toDouble)
        showResult()
      }

      consBox.onchange =
        (event : dom.Event) => {
          data = data.copy(consumption = consBox.value.toDouble)
          showResult()
        }

    cflsBox.onchange =
      (event : dom.Event) => {
        cfls = cflsBox.value.toInt
        ledUpdate()
      }

    bulbsBox.onchange =
      (event : dom.Event) => {
        bulbs = bulbsBox.value.toInt
        ledUpdate()
      }

}
