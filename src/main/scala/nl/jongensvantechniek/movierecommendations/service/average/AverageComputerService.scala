package nl.jongensvantechniek.movierecommendations.service.average

import org.apache.spark.SparkContext

/**
  *
  */
object AverageComputerService extends AveragesCounter {

  /**
    *
    * @inheritdoc
    */
  def computeAverageOfFriendsByAge(sparkContext: SparkContext,
                                   dataSourcePath:String = "datasets/custom/fakefriends.csv"):  Array[(Int, Int)] = {
    getSortedAveragesByAge(sparkContext, dataSourcePath)
  }
}
