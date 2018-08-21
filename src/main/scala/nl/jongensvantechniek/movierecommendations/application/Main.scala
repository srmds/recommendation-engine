package nl.jongensvantechniek.movierecommendations.application

import nl.jongensvantechniek.movierecommendations.service.distribution.DistributionComputerService

/**
  * The main Spark driver application that is execute a given job.
  */
object Main extends InitSpark {

  /**
    *
    * @param args to pass to main driver class
    */
  def main(args: Array[String]): Unit = {

    val version = spark.version
    val dataSetPath = "datasets/movielens/ml-100k/u.data"
    val distributionService = DistributionComputerService

    log.info(s"Spark version: $version")
    log.debug(s"Using dataset: $dataSetPath")

    // Get the distribution of ratings for the loaded dataset
    val distributionOfRatings = distributionService.computeDistributionOfRatings(sc, dataSetPath)

    // Print out the sorted ratings count as a distribution of ratings
    log.debug("|rating, count|")
    distributionOfRatings.foreach(rating => log.debug(rating))

    //Close the spark context and therefore end the Spark job
    close()
  }
}