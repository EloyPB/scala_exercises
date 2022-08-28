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
    else if l1.lat == -l2.lat && (l1.lon == l2.lon + 180 || l1.lon == l2.lon - 180) then EarthRadius * Pi
    else EarthRadius * acos(sin(l1.lat) * sin(l2.lat) + cos(l1.lat) * cos(l2.lat) * cos(l2.lon - l1.lon))


  /**
    * @param temperatures Known temperatures: pairs containing a location and the temperature at this location
    * @param location Location where to predict the temperature
    * @return The predicted temperature at `location`
    */
  def predictTemperature(temperatures: Iterable[(Location, Temperature)], location: Location): Temperature =
    ???

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


