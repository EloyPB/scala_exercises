package observatory

import com.sksamuel.scrimage.implicits.*
import scala.util.Properties.isWin

object Main extends App:

  import org.apache.log4j.{Level, Logger}
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)

  if (isWin) System.setProperty("hadoop.home.dir", System.getProperty("user.dir") + "\\winutils\\hadoop-3.3.1")

  //  Using spark
  val df = Extraction.locateTemperaturesSpark(1975, "/stations.csv", "/1975.csv")
  Extraction.averageTempSpark(df)

end Main

