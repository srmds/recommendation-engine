package nl.jongensvantechniek.movierecommendations.application

import nl.jongensvantechniek.movierecommendations.service.average.AverageComputerService
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
    val movieLensDataSetPath = "datasets/movielens/ml-100k/u.data"
    val fakeFriendsDataSetPath = "datasets/custom/fakefriends.csv"

    val distributionService = DistributionComputerService
    val averageService = AverageComputerService

    log.info(s"Spark version: $version")
    log.debug("*****************************************************************************************************")

    log.debug(s"Get the distribution of ratings for dataset: $movieLensDataSetPath")
    val distributionOfRatings = distributionService.computeDistributionOfRatings(sc, movieLensDataSetPath)
    log.debug("|rating, count of rating|")
    distributionOfRatings.foreach(rating => log.debug(rating))

    log.debug("*****************************************************************************************************")

    log.debug(s"Get the averages of friends by ages for dataset: $fakeFriendsDataSetPath")
    val avergesOfFriendsByAge = averageService.computeAverageOfFriendsByAge(sc, fakeFriendsDataSetPath)
    log.debug("|age, average of friends|")
    avergesOfFriendsByAge.sorted.foreach(average => log.debug(average))
    log.debug("*****************************************************************************************************")

    //Close the spark context and therefore end the Spark job
    close()
  }
}