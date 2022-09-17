package observatory

import com.sksamuel.scrimage.ImmutableImage
import com.sksamuel.scrimage.pixels.Pixel
import com.sksamuel.scrimage.metadata.ImageMetadata
import com.sksamuel.scrimage.implicits.*
import observatory.Visualization.{interpolateColor, predictTemperature}

import scala.collection.parallel.CollectionConverters.given

/**
  * 3rd milestone: interactive visualization
  */
object Interaction extends InteractionInterface:

  val toDeg: Double = 180 / math.Pi

  /**
    * @param tile Tile coordinates
    * @return The latitude and longitude of the top-left corner of the tile, as per http://wiki.openstreetmap.org/wiki/Slippy_map_tilenames
    */
  def tileLocation(tile: Tile): Location =
    val n = 1 << tile.zoom
    val lon = tile.x.toDouble / n * 360 - 180
    val lat = math.atan(math.sinh((1 - tile.y.toDouble / n * 2) * math.Pi)) * toDeg
    Location(lat, lon)

  /**
    * @param temperatures Known temperatures
    * @param colors Color scale
    * @param tile Tile coordinates
    * @return A 256×256 image showing the contents of the given tile
    */
  def tile(temperatures: Iterable[(Location, Temperature)], colors: Iterable[(Temperature, Color)], tile: Tile): ImmutableImage =
    val subTileX = tile.x * 256
    val subTileY = tile.y * 256
    val subTileZoom = tile.zoom + 8
    val pixels = (0 until 256).par.flatMap(y => (0 until 256).map(x => {
      val location = tileLocation(Tile(subTileX + x, subTileY + y, subTileZoom))
      val color = interpolateColor(colors, predictTemperature(temperatures, location))
      Pixel(y, x, color.red, color.green, color.blue, 127)
    }))
    ImmutableImage.wrapPixels(256, 256, pixels.toArray, ImageMetadata.empty)


  val colors: Seq[(Double, Color)] = List((60.0, Color(255, 255, 255)), (32.0, Color(255, 0, 0)), (12.0, Color(255, 255, 0)),
    (0.0, Color(0, 255, 255)), (-15.0, Color(0, 0, 255)), (-27.0, Color(255, 0, 255)), (-50.0, Color(33, 0, 107)),
    (-60.0, Color(0, 0, 0)))

  def generateImage(year: Year, t: Tile, temperatures: Iterable[(Location, Temperature)]): Unit =
    val image = tile(temperatures, colors, t)
    val file = java.io.File(s"target/temperatures/$year/${t.zoom}/${t.x}-${t.y}.png")
    if !file.exists() then java.io.File(file.getParent).mkdirs()
    image.output(new java.io.File(s"target/temperatures/$year/${t.zoom}/${t.x}-${t.y}.png"))



  /**
    * Generates all the tiles for zoom levels 0 to 3 (included), for all the given years.
    * @param yearlyData Sequence of (year, data), where `data` is some data associated with
    *                   `year`. The type of `data` can be anything.
    * @param generateImage Function that generates an image given a year, a zoom level, the x and
    *                      y coordinates of the tile and the data to build the image from
    */
  def generateTiles[Data](
    yearlyData: Iterable[(Year, Data)],
    generateImage: (Year, Tile, Data) => Unit
  ): Unit =
    for
      data <- yearlyData
      zoom <- 0 to 3
      x <- 0 until (1 << zoom)
      y <- 0 until (1 << zoom)
    do generateImage(data._1, Tile(x, y, zoom), data._2)



