package observatory

import java.time.LocalDate
import scala.io.Source
import org.apache.spark.sql.*
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._
import org.apache.spark.rdd.RDD
import spark.implicits._

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


  def locateTemperaturesSpark(year: Year, stationsFile: String, temperaturesFile: String): DataFrame =

    val tempSchema = StructType(Array(
      StructField("stn", IntegerType, true),
      StructField("wban", IntegerType, true),
      StructField("month", IntegerType, true),
      StructField("day", IntegerType, true),
      StructField("temperature", DoubleType, true),
    ))

//    val temperatures = spark.read.format("csv").schema(tempSchema)
//      .load(s"src/main/resources/$temperaturesFile").filter(col("temperature") =!= 9999.9)

    val tempLines = Source.fromInputStream(getClass.getResourceAsStream(temperaturesFile), "utf-8").getLines().toList
    val tempDS: Dataset[String] = spark.sparkContext.parallelize(tempLines).toDS
    val temperatures: DataFrame = spark.read.schema(tempSchema).csv(tempDS)
      .filter(col("temperature") =!= 9999.9)
      .withColumn("temp", (col("temperature") - 32) / 1.8)
      .drop("temperature")

    val stationsSchema = StructType(Array(
      StructField("stn", IntegerType, true),
      StructField("wban", IntegerType, true),
      StructField("lat", DoubleType, true),
      StructField("long", DoubleType, true)
    ))

//    val stations = spark.read.format("csv").schema(stationsSchema)
//      .load(s"src/main/resources/$stationsFile").na.drop(Seq("lat", "long"))

    val stationsLines = Source.fromInputStream(getClass.getResourceAsStream(stationsFile), "utf-8").getLines().toList
    val stationsDS: Dataset[String] = spark.sparkContext.parallelize(stationsLines).toDS
    val stations: DataFrame = spark.read.schema(stationsSchema).csv(stationsDS)
      .na.drop(Seq("lat", "long"))

    val joined = stations.join(temperatures, stations("stn").eqNullSafe(temperatures("stn"))
      && stations("wban").eqNullSafe(temperatures("wban"))).drop(temperatures("wban")).drop(temperatures("stn"))
    joined

  
  def locateTemperatures(year: Year, stationsFile: String, temperaturesFile: String): Iterable[(LocalDate, Location, Temperature)] =
    val records = locateTemperaturesSpark(year, stationsFile, temperaturesFile).collect()
    records.map(row => (LocalDate.of(year, row(4).asInstanceOf[Int], row(5).asInstanceOf[Int]),
      Location(row(2).asInstanceOf[Double], row(3).asInstanceOf[Double]), row(6).asInstanceOf[Double]))

  
  def averageTempSpark(df: DataFrame): Iterable[(Location, Temperature)] =
    val average = df.groupBy("lat", "long").agg(avg("temp")).collect()
    average.map(row => (Location(row.getAs[Double](0), row.getAs[Double](1)), row.getAs[Double](2)))
  
  /**
    * @param records A sequence containing triplets (date, location, temperature)
    * @return A sequence containing, for each location, the average temperature over the year.
    */
  def locationYearlyAverageRecords(records: Iterable[(LocalDate, Location, Temperature)]): Iterable[(Location, Temperature)] =
    val recordsRDD = spark.sparkContext.parallelize(records.toSeq)
    val temps = recordsRDD.groupBy(r => r._2).mapValues(iterable => iterable.map(_._3))
    val averageTemps = temps.mapValues(t => t.sum / t.size)
    averageTemps.collect().toSeq



