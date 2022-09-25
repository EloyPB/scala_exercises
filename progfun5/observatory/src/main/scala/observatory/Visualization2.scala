package observatory

import com.sksamuel.scrimage.ImmutableImage
import com.sksamuel.scrimage.pixels.Pixel
import com.sksamuel.scrimage.metadata.ImageMetadata
import scala.collection.parallel.CollectionConverters.given

/**
  * 5th milestone: value-added information visualization
  */
object Visualization2 extends Visualization2Interface:

  /**
    * @param point (x, y) coordinates of a point in the grid cell
    * @param d00 Top-left value
    * @param d01 Bottom-left value
    * @param d10 Top-right value
    * @param d11 Bottom-right value
    * @return A guess of the value at (x, y) based on the four known values, using bilinear interpolation
    *         See https://en.wikipedia.org/wiki/Bilinear_interpolation#Unit_Square
    */
  def bilinearInterpolation(
    point: CellPoint,
    d00: Temperature,
    d01: Temperature,
    d10: Temperature,
    d11: Temperature
  ): Temperature =
    d00 * (1 - point.x) * (1 - point.y) + d01 * (1 - point.x) * point.y
      + d10 * point.x * (1 - point.y) + d11 * point.x * point.y

  /**
    * @param grid Grid to visualize
    * @param colors Color scale to use
    * @param tile Tile coordinates to visualize
    * @return The image of the tile at (x, y, zoom) showing the grid using the given color scale
    */
  def visualizeGrid(grid: GridLocation => Temperature, colors: Iterable[(Temperature, Color)], tile: Tile): ImmutableImage =
    val subTileX = tile.x * 256
    val subTileY = tile.y * 256
    val subTileZoom = tile.zoom + 8
    val pixels = (0 until 256).par.flatMap(y => (0 until 256).map(x => {
      val loc = Interaction.tileLocation(Tile(subTileX + x, subTileY + y, subTileZoom))

      val xl = loc.lon.floor.toInt
      val xr = loc.lon.ceil.toInt
      val yt = loc.lat.ceil.toInt
      val yb = loc.lat.floor.toInt

      val t00 = grid(GridLocation(yt, xl))
      val t01 = grid(GridLocation(yb, xl))
      val t10 = grid(GridLocation(yt, xr))
      val t11 = grid(GridLocation(yb, xr))

      val point = CellPoint(loc.lon - xl, yt - loc.lat)

      val color = Visualization.interpolateColor(colors, bilinearInterpolation(point, t00, t01, t10, t11))
      Pixel(y, x, color.red, color.green, color.blue, 127)
    }))
    ImmutableImage.wrapPixels(256, 256, pixels.toArray, ImageMetadata.empty)
