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

    val areaBox = input(`type`:= "text", size := "5").render

    val consBox = input(`type`:= "text", size := "5", value := 500).render

    // val effBox = input(`type`:= "text", size := "5", value := 135).render

    val inpDiv =
      div(`class`:= "col-md-4")(
        div(),
        h3("Enter the data below"),
        table(`class` := "table")(
          tbody(
          tr(td("House area (in square feet): "), td(areaBox)),
          tr(td("Monthly consumption (in units): "), td(consBox))
          // tr(td("Efficiency (standard is 135): "), td(effBox))
        )
      )//,
        // div("House area:", span(" "), areaBox, span(" square feet")),
        // div("Efficiency:", span(" "), effBox, span(" (standard: 135)"))
      ).render

    val resDiv = div(`class` := "col-md-6")(
      h3("Results"),
      table(`class` := "table")(
      tbody(
        tr(td(span("Revenue (per month): ")), td("")),
        tr(td(span("Savings (per month): ")), td("")),
        tr(td(span("Total gains (per month): ")), td("")),
        tr(td(span("Cost: ")), td("")),
        tr(td(span("Return on Investment: ")), td(""))
      )
      )

    ).render

    val viewDiv = div(`class` := "row")(inpDiv, resDiv).render

    jsDiv.appendChild(viewDiv)

    def showResult() = {
      resDiv.innerHTML = ""
      resDiv.appendChild(
        div(
          div(),
          h3("Results"),
          table(`class` := "table")(
          tbody(
            tr(td(span("Revenue (per month): ")), td(span(`class`:= "bg-info")(s"${data.revenue}"))),
            tr(td(span("Savings (per month): ")), td(span(`class`:= "bg-info")(s"${data.savings}"))),
            tr(td(span("Total gains (per month): ")), td(span(`class`:= "bg-info")(s"${data.gains}"))),
            tr(td(span("Cost: ")), td(span(`class`:= "bg-info")(s"${data.cost}"))),
            tr(td(span("Return on Investment: ")), td(span(`class`:= "bg-info")(s"${data.roi}")))
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
