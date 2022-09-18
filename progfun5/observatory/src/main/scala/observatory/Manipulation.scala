package observatory

import scala.collection.parallel.CollectionConverters.given
import scala.math.{cos, sin}

/**
  * 4th milestone: value-added information
  */
object Manipulation extends ManipulationInterface:

  /**
    * @param temperatures Known temperatures
    * @return A function that, given a latitude in [-89, 90] and a longitude in [-180, 179],
    *         returns the predicted temperature at this location
    */
  def makeGrid(temperatures: Iterable[(Location, Temperature)]): GridLocation => Temperature =
    val grid = Array.ofDim[Double](180, 360)

    val data = temperatures.map((loc, temp) => (loc, sin(loc.latR), cos(loc.latR), temp))

    (0 until 180).par.foreach(x => (0 until 360).foreach(y =>
      grid(x)(y) = Visualization.predictTemperature2(data, Location(90 - x, y - 180))))

    (loc: GridLocation) => grid(90 - loc.lat)(loc.lon + 180)

  /**
    * @param temperaturess Sequence of known temperatures over the years (each element of the collection
    *                      is a collection of pairs of location and temperature)
    * @return A function that, given a latitude and a longitude, returns the average temperature at this location
    */
  def average(temperaturess: Iterable[Iterable[(Location, Temperature)]]): GridLocation => Temperature =
    val temperatureLocators = temperaturess.map(temperatures => makeGrid(temperatures))

    val averages = Array.fill[Double](180, 360){0}

    for
      x <- (0 until 180).par
      y <- (0 until 360)
      temperatureLocator <- temperatureLocators
    do averages(x)(y) += temperatureLocator(GridLocation(90 - x, y - 180))

    for
      x <- (0 until 180).par
      y <- (0 until 360)
    do averages(x)(y) /= temperaturess.size

    (loc: GridLocation) => averages(90 - loc.lat)(loc.lon + 180)

  /**
    * @param temperatures Known temperatures
    * @param normals A grid containing the “normal” temperatures
    * @return A grid containing the deviations compared to the normal temperatures
    */
  def deviation(temperatures: Iterable[(Location, Temperature)], normals: GridLocation => Temperature): GridLocation => Temperature =
    val temperatureLocator = makeGrid(temperatures)

    val deviations = Array.ofDim[Double](180, 360)

    for
      x <- (0 until 180).par
      y <- (0 until 360)
    do {
      val location = GridLocation(90 - x, y - 180)
      deviations(x)(y) = temperatureLocator(location) - normals(location)
    }

    (loc: GridLocation) => deviations(90 - loc.lat)(loc.lon + 180)


