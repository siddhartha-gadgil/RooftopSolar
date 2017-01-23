package solar

case class SolarData(area: Double,  consumption: Double = 500, tariff : Double = 7){
  import SolarData._

  val capacity = area / 100 // area in square feet

  val cost = capacity * costPerUnit // in ruppees

  val generated = capacity * efficiency  // units (KWh) generated in a month

  val surplus = generated - consumption // units (KWh) in a month

  val revenue = math.max(surplus * tariff, 0) // ruppees in a month

  val savings = math.min(generated, consumption) * billPerUnit

  val gains = savings + revenue

  lazy val roi = gains * 12 / cost * 100
}

object SolarData{
  val costPerUnit = 85000.0

  val billPerUnit : Double = 3

  val efficiency: Double = 4.5 * 30

}

object Led{
  def savings(bulbs: Int, cfls: Int) = (3930 * bulbs) + (14330 * cfls)
}
