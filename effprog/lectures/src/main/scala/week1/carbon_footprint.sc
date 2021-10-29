enum Network:
  case Fixed, Mobile

case class Experience(duration: Int, definition: Double, network: Network)

val lowQuality  = 0.3 // MB/s
val highQuality = 0.6 // MB/s

val thirtyMinutes = 30 * 60 // seconds

val highQualityAndMobile =
  Experience(thirtyMinutes, highQuality, Network.Mobile)

val lowQualityAndFixed =
  Experience(thirtyMinutes, lowQuality, Network.Fixed)

val dataCenterEnergy = 0.000072
val kgC02PerKwh = 0.5

def networkEnergy(network: Network): Double = network match
  case Network.Fixed  => 0.00043
  case Network.Mobile => 0.00088

def footprint(experience: Experience): Double =
  val megabytes = experience.duration * experience.definition
  val energy    = dataCenterEnergy + networkEnergy(experience.network)
  energy * megabytes * kgC02PerKwh

footprint(lowQualityAndFixed)
footprint(highQualityAndMobile)