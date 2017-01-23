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

    var data = SolarData(0)

    import scalatags.JsDom.implicits._

    val areaBox = input(`class` := "pull-right")(`type`:= "text", size := "5").render

    val consBox = input(`class` := "pull-right")(`type`:= "text", size := "5", value := 500).render

    // val effBox = input(`type`:= "text", size := "5", value := 135).render

    val inpDiv =
      div(
        div(),
        table(`class` := "table table-striped")(
          caption("Household data"),
          tbody(
          tr(td("Roof area (in square feet): "), td(areaBox)),
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
        tr(td(span("Revenue (per month): ")), td("")),
        tr(td(span("Savings (per month): ")), td("")),
        tr(td(span("Total gains (per month): ")), td("")),
        tr(td(span("Cost: ")), td("")),
        tr(td(span("Return on Investment: ")), td(""))
      )
      )

    ).render

    val roofTopDiv =
      div(`class` := "col-md-6")(
        h2("Rooftop Solar"),
      inpDiv, resDiv).render

    val ledDiv =
      div(`class` := "col-md-6")(
        h2("LED lighting"))

    val viewDiv = div(`class` := "row")(roofTopDiv, ledDiv).render

    jsDiv.appendChild(viewDiv)

    def showResult() = {
      resDiv.innerHTML = ""
      resDiv.appendChild(
        div(
          table(`class` := "table table-striped")(
            caption("Your gains if you install Rooftop Solar"),
          tbody(
            tr(td(span("Revenue (per month): ")), td(span(`class`:= "pull-right bg-info")(s"${data.revenue}"))),
            tr(td(span("Savings (per month): ")), td(span(`class`:= "pull-right bg-info")(s"${data.savings}"))),
            tr(td(span("Total gains (per month): ")), td(span(`class`:= "pull-right bg-info")(s"${data.gains}"))),
            tr(td(span("Cost: ")), td(span(`class`:= "pull-right bg-info")(s"${data.cost}"))),
            tr(td(span("Return on Investment: ")), td(span(`class`:= "pull-right bg-info")(f"${data.roi}%2.1f"+"%")))
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


    // effBox.onchange =
    //     (event : dom.Event) => {
    //       data = data.copy(efficiency = areaBox.value.toDouble)
    //       showResult()
    //     }

}
