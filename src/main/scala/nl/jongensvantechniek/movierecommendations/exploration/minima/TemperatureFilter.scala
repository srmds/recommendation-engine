package nl.jongensvantechniek.movierecommendations.exploration.minima

import nl.jongensvantechniek.movierecommendations.application.SparkManager

import scala.math.min

/**
  *
  */
object TemperatureFilter {

  private val sparkManager = SparkManager

  /**
    *
    * @param dataSourcePath
    * @param filterType
    * @return
    */
  def getTemperaturesForStations(dataSourcePath: String,
                                 filterType: FilterType.FilterType): Seq[(String, Float)] = {
    sparkManager.init("Min/Max temperatures")
    // Load up each line of the ratings data into an RDD
    val lines = sparkManager.sc.textFile(dataSourcePath)

    // Split by commas
    // Extract the stationId, entryType and temperature as Fahrenheit
    // and create a tuple that is our result
    val rdd = lines.map((lineItem) => {
      def parseLine(line:String) = {
        val fields = line.split(",")
        val temperature = fields(3).toFloat * 0.1f * (9.0f / 5.0f) + 32.0f
        (fields(0), fields(2), temperature)
      }

      parseLine(lineItem)
    })

    // Filter out all but TMIN entries
    val minTemps = rdd.filter(x => x._2 == filterType.toString)

    // Convert to (stationId, temperature)
    val stationTemps = minTemps.map(x => (x._1, x._3.toFloat))

    // Reduce (stationId, temperature) by stationId retaining the minimum temperature found
    val minTempsByStation = stationTemps.reduceByKey( (x,y) => min(x,y))

    // Collect the results from the RDD (This kicks off computing the DAG and actually executes the job)
    val result = minTempsByStation.collect()
    sparkManager.close()

    result
  }
}
