package observatory

import java.time.LocalDate
import org.apache.spark.sql.*
import org.apache.spark.sql.types._

/**
  * 1st milestone: data extraction
  */
object Extraction extends ExtractionInterface:

  /**
    * @param year             Year number
    * @param stationsFile     Path of the stations resource file to use (e.g. "/stations.csv")
    * @param temperaturesFile Path of the temperatures resource file to use (e.g. "/1975.csv")
    * @return A sequence containing triplets (date, location, temperature)
    */


  def locateTemperaturesSpark(year: Year, stationsFile: String, temperaturesFile: String): Unit =
    val tempSchema = StructType(Array(
      StructField("stn", IntegerType, true),
      StructField("wban", IntegerType, true),
      StructField("month", IntegerType, true),
      StructField("day", IntegerType, true),
      StructField("temperature", DoubleType, true),
    ))
    val temperatures = spark.read.format("csv").schema(tempSchema)
      .load(s"src/main/resources/$temperaturesFile")
    temperatures.show()

    val locSchema = StructType(Array(
      StructField("stn", IntegerType, true),
      StructField("wban", IntegerType, true),
      StructField("lat", DoubleType, true),
      StructField("long", DoubleType, true)
    ))
    val stations = spark.read.format("csv").schema(locSchema)
      .load(s"src/main/resources/$stationsFile")
    stations.show()

//    val joined = stations.join(temperatures, Seq("stn", "wban"))
    val joined = stations.join(temperatures, stations("stn").eqNullSafe(temperatures("stn")) && stations("wban").eqNullSafe(temperatures("wban")))
    joined.show()

  def locateTemperatures(year: Year, stationsFile: String, temperaturesFile: String): Iterable[(LocalDate, Location, Temperature)] =
    ???

  /**
    * @param records A sequence containing triplets (date, location, temperature)
    * @return A sequence containing, for each location, the average temperature over the year.
    */
  def locationYearlyAverageRecords(records: Iterable[(LocalDate, Location, Temperature)]): Iterable[(Location, Temperature)] =
    ???

