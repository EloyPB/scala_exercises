package observatory

import org.apache.spark.sql.SparkSession

type Temperature = Double // °C, introduced in Week 1
type Year = Int // Calendar year, introduced in Week 1

val spark = SparkSession
  .builder()
  .master("local")
  .appName("Observatory")
  .getOrCreate()
