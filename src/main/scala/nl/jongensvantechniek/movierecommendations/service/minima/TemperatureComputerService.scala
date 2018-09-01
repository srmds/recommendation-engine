package nl.jongensvantechniek.movierecommendations.service.minima

import nl.jongensvantechniek.movierecommendations.service.minima.FilterType.FilterType
import org.apache.spark.SparkContext

object TemperatureComputerService extends TemperatureFilter {

  /**
    *
    * @inheritdoc
    */
  def computeFilteredTemperatures(sparkContext: SparkContext,
                                  dataSourcePath:String = "datasets/weather/temperatures.csv",
                                  filterType: FilterType): Seq[(String, Float)] = {
    getTemperaturesForStations(sparkContext, dataSourcePath, filterType)
  }

}