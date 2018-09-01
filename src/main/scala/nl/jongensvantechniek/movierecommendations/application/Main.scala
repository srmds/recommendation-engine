package nl.jongensvantechniek.movierecommendations.application

import nl.jongensvantechniek.movierecommendations.exploration.average.AveragesCounter
import nl.jongensvantechniek.movierecommendations.exploration.count.WordCounter
import nl.jongensvantechniek.movierecommendations.exploration.spent.SpendingCounter
import nl.jongensvantechniek.movierecommendations.service.movie.MovieRecommendationService
import nl.jongensvantechniek.movierecommendations.exploration.minima.{FilterType, TemperatureFilter}
import scala.annotation.switch
import scala.io.StdIn.readLine

/**
  * The main Spark driver application that is execute a given job.
  */
object Main extends InitSpark {

  private val version = spark.version

  private val movieLensDataSetPath = "datasets/movielens/ml-100k/u.data"
  private val fakeFriendsDataSetPath = "datasets/friends/fakefriends.csv"
  private val temperaturesDataSetPath = "datasets/weather/temperatures.csv"
  private val bookDataSetPath = "datasets/book/book.txt"
  private val customerOrderDataset = "datasets/spending/customer_orders.csv"

  private val movieRecommendationService = MovieRecommendationService
  private val averagesCounter = AveragesCounter
  private val temperatureFilter = TemperatureFilter
  private val wordCounter = WordCounter
  private val spendingCounter = SpendingCounter

  /**
    *
    * @param args to pass to main driver class
    */
  def main(args: Array[String]): Unit = {
    log.info(s"Spark version: $version")

    menu(readLine("---------------------------------------<[ Menu ]>------------------------------------------" +
      "\n\nSelect an option to run:\n\n1) Distribution\n2) Average\n3) Filtering\n4) Words Count\n5) Spending Amount\nq) Exit\n"))
  }

  /**
    *
    * @param input
    */
  def menu(input: String): Unit = {

    (input: @switch) match {

      case "q" => {
        //Close the spark context and therefore end the Spark job
        close()

        System.exit(1)
      }

      case "1" => {
        log.debug("---------------------------------------<[ Distribution ]>------------------------------------------")
        log.debug(s"Get the distribution of ratings for dataset: $movieLensDataSetPath")
        val distributionOfRatings = movieRecommendationService.getSortedRatingsCount(sc, movieLensDataSetPath)
        log.debug("|rating, count of rating|")
        distributionOfRatings.foreach(rating => log.debug(rating))
      }

      case "2" => {
        log.debug("---------------------------------------<[ Average ]>-----------------------------------------------")
        log.debug(s"Get the averages of friends by ages for dataset: $fakeFriendsDataSetPath")
        val avergesOfFriendsByAge = averagesCounter.getSortedAveragesByAge(sc, fakeFriendsDataSetPath)
        log.debug("|age, average of friends|")
        avergesOfFriendsByAge.sorted.foreach(average => log.debug(average))
      }

      case "3" => {
        log.debug("---------------------------------------<[ Filtering ]>---------------------------------------------")
        log.debug(s"Get MINIMUM of temperatures for dataset: $temperaturesDataSetPath")
        val minimaTemperatures = temperatureFilter.getTemperaturesForStations(
          sc,
          temperaturesDataSetPath,
          FilterType.MIN_TEMPERATURE
        )
        log.debug("|stationId, minTemp F|")
        minimaTemperatures.sorted.foreach(result => {
          log.debug(result)
        })

        log.debug("-------------------------")

        log.debug(s"Get MAXIMUM of temperatures for dataset: $temperaturesDataSetPath")
        val maximaTemperatures = temperatureFilter.getTemperaturesForStations(
          sc,
          temperaturesDataSetPath,
          FilterType.MAX_TEMPERATURE
        )
        log.debug("|stationId, maxTemp F|")
        maximaTemperatures.sorted.foreach(result => {
          log.debug(result)
        })
      }

      case "4" => {
        log.debug("---------------------------------------<[ Words Count ]>-------------------------------------------------")
        log.debug(s"Get the count of words for dataset: $bookDataSetPath")
        val wordCounts = wordCounter.getCountOfEachWord(sc, bookDataSetPath)
        log.debug("|count, word|")
        wordCounts.foreach(result => log.debug(result))
      }

      case "5" => {
        log.debug("---------------------------------------<[ Spending Amount Count ]>-------------------------------------------------")
        log.debug(s"Get spending amount per customer for dataset: $customerOrderDataset")
        val spendings = spendingCounter.getSpendingAmountPerCustomer(sc, customerOrderDataset)
        log.debug("|amount, customerId|")
        spendings.foreach(result => log.debug(result))
      }

      case default => {
        log.warn("Not a valid option")
        return
      }

    }

    menu(readLine("Run: \n1) Distribution\n2) Average\n3) Filtering\n4) Count\nq for Quit\n\nYour choice: "))
  }

}