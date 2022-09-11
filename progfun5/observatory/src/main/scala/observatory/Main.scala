package observatory

import com.sksamuel.scrimage.implicits.*
import scala.util.Properties.isWin

object Main extends App:

  import org.apache.log4j.{Level, Logger}
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)

  if (isWin) System.setProperty("hadoop.home.dir", System.getProperty("user.dir") + "\\winutils\\hadoop-3.3.1")

  //  Using spark
  val df = Extraction.locateTemperaturesSpark(1975, "/stations.csv", "/2015.csv")
  val temperatures = Extraction.averageTempSpark(df)
  println(s"Num records: ${temperatures.size}")

  val colors = List((60.0, Color(255, 255, 255)), (32.0, Color(255, 0, 0)), (12.0, Color(255, 255, 0)),
    (0.0, Color(0, 255, 255)), (-15.0, Color(0, 0, 255)), (-27.0, Color(255, 0, 255)), (-50.0, Color(33, 0, 107)),
    (-60.0, Color(0, 0, 0)))

  val image = Visualization.visualize(temperatures, colors)
  image.output(new java.io.File("target/2015.png"))

end Main

