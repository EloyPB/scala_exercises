package observatory

import com.sksamuel.scrimage.ImmutableImage
import com.sksamuel.scrimage.pixels.Pixel
import com.sksamuel.scrimage.metadata.ImageMetadata
import com.sksamuel.scrimage.implicits.given
import scala.collection.parallel.CollectionConverters.given
import scala.math.{acos, sin, cos, Pi}

/**
  * 2nd milestone: basic visualization
  */
object Visualization extends VisualizationInterface:
  val EarthRadius = 6371  // km

  def greatCircleDistance(l1: Location, l2: Location): Double =
    if l1 == l2 then 0
    else if l1.lat == -l2.lat && (l1.lon == l2.lon + 180 || l1.lon == l2.lon - 180) then
      EarthRadius * Pi
    else
      EarthRadius * acos(sin(l1.latR) * sin(l2.latR) + cos(l1.latR) * cos(l2.latR) * cos(l2.lonR - l1.lonR))


  /**
    * @param temperatures Known temperatures: pairs containing a location and the temperature at this location
    * @param location Location where to predict the temperature
    * @return The predicted temperature at `location`
    */
  def predictTemperature(temperatures: Iterable[(Location, Temperature)], location: Location): Temperature =
    println(s"predicting temperature for $location")
    val distances = temperatures.map((loc, _) => greatCircleDistance(loc, location))
    if distances.exists(d => d < 1) then
      temperatures.zip(distances).filter((_, d) => d < 1).head._1._2
    else
      val weights = distances.map(d => 1 / math.pow(d, 2))
      temperatures.zip(weights).map((p, w) => p._2 * w).sum / weights.sum


  /**
    * @param points Pairs containing a value and its associated color
    * @param value The value to interpolate
    * @return The color that corresponds to `value`, according to the color scale defined by `points`
    */
  def interpolateColor(points: Iterable[(Temperature, Color)], value: Temperature): Color =
    val sorted = points.toList.sortBy(_._1)
    val pair = sorted.zip(sorted.tail).find((p1, p2) => p1._1 <= value && value < p2._1)
    pair match
      case Some((p1, p2)) =>
        val diff = p2._1 - p1._1
        val right = (value - p1._1) / diff
        val left = (p2._1 - value) / diff
        Color((p1._2.red * left + p2._2.red * right).round.toInt,
          (p1._2.green * left + p2._2.green * right).round.toInt,
          (p1._2.blue * left + p2._2.blue * right).round.toInt)
      case None =>
        if value < sorted.head._1 then sorted.head._2 else sorted.last._2

  /**
    * @param temperatures Known temperatures
    * @param colors Color scale
    * @return A 360×180 image where each pixel shows the predicted temperature at its location
    */
  def visualize(temperatures: Iterable[(Location, Temperature)], colors: Iterable[(Temperature, Color)]): ImmutableImage =
    val width = 360
    val height = 180
    val pixels = (0 until height).par.flatMap(x => (0 until width).map(y => {
      val color = interpolateColor(colors, predictTemperature(temperatures, Location(90 - x, y - 180)))
      Pixel(x, y, color.red, color.green, color.blue, 255)
    }))
    ImmutableImage.wrapPixels(width, height, pixels.toArray, ImageMetadata.empty)


