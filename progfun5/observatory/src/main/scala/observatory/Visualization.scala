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
      val lat1 = l1.lat / 180 * Pi
      val lon1 = l1.lon / 180 * Pi
      val lat2 = l2.lat / 180 * Pi
      val lon2 = l2.lon / 180 * Pi
      EarthRadius * acos(sin(lat1) * sin(lat2) + cos(lat1) * cos(lat2) * cos(lon2 - lon1))


  /**
    * @param temperatures Known temperatures: pairs containing a location and the temperature at this location
    * @param location Location where to predict the temperature
    * @return The predicted temperature at `location`
    */
  def predictTemperature(temperatures: Iterable[(Location, Temperature)], location: Location): Temperature =
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
    ???

  /**
    * @param temperatures Known temperatures
    * @param colors Color scale
    * @return A 360×180 image where each pixel shows the predicted temperature at its location
    */
  def visualize(temperatures: Iterable[(Location, Temperature)], colors: Iterable[(Temperature, Color)]): ImmutableImage =
    ???


