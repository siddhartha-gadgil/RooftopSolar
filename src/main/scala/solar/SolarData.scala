package solar

case class SolarData(area: Double,  consumption: Double = 200, tariff : Double = 7.08){
  import SolarData._

  val capacity = area / 100 // area in square feet

  val cost = capacity * costPerUnit // in ruppees

  val generated = capacity * efficiency  // units (KWh) generated in a month

  val surplus = generated - consumption // units (KWh) in a month

  val revenue = math.max(surplus * tariff, 0) // ruppees in a month

  val savings = bill(consumption) - bill(math.max(consumption - generated, 0))

  val gains = savings + revenue

  lazy val paybackPeriod = cost / gains
}

object SolarData{
  val costPerUnit = 85000.0

  val billPerUnit : Double = 3

  val efficiency: Double = 4.0 * 30

  import math._

  def bill(units: Double) = {
    val lowestUnits = math.min(units, 30)

    val middleUnits = math.min(units - lowestUnits, 70)

    val highUnits = math.min(units - lowestUnits - middleUnits, 100)

    val topUnits = max(units - 200, 0)

    (3 * lowestUnits) + (4.4 * middleUnits) + (5.9 * highUnits) + (6.9 * topUnits)
  }

}

object Led{
  def savings(bulbs: Int, cfls: Int) = (3930 * bulbs) + (14330 * cfls)
}
