package nl.jongensvantechniek.movierecommendations.service.distribution

import org.apache.spark.SparkContext

object DistributionComputerService extends RatingsCounter {

  /**
    *
    * @inheritdoc
    */
  def computeDistributionOfRatings(sparkContext: SparkContext,
                                   dataSourcePath:String = "datasets/movielens/ml-100k/u.data"): Seq[(String, Long)] = {
    getSortedRatingsCount(sparkContext, dataSourcePath)
  }

}