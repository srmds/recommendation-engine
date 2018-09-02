package nl.jongensvantechniek.movierecommendations.application

import nl.jongensvantechniek.movierecommendations.exploration.average.AveragesCounter
import nl.jongensvantechniek.movierecommendations.exploration.count.WordCounter
import nl.jongensvantechniek.movierecommendations.exploration.minima.{FilterType, TemperatureFilter}
import nl.jongensvantechniek.movierecommendations.exploration.spent.SpendingCounter
import nl.jongensvantechniek.movierecommendations.service.movie.MovieRecommendationService
import scala.annotation.switch
import scala.io.StdIn.readLine

/**
  * The main Spark driver application that is execute a given job.
  */
object Main {
  private val movieLensIdsDataSetPath = "datasets/movielens/ml-100k/u.data"
  private val movieLensTitlesDataSetPath = "datasets/movielens/ml-100k/u.item"

  private val fakeFriendsDataSetPath = "datasets/friends/fakefriends.csv"
  private val temperaturesDataSetPath = "datasets/weather/temperatures.csv"
  private val bookDataSetPath = "datasets/book/book.txt"
  private val customerOrderDataset = "datasets/spending/customer_orders.csv"

  private val movieRecommendationService =  MovieRecommendationService
  private val averagesCounter = AveragesCounter
  private val temperatureFilter = TemperatureFilter
  private val wordCounter = WordCounter
  private val spendingCounter = SpendingCounter

  private val menuOptions =
    "---------------------------------------<[ Menu ]>-------------------------------------------------" +
    "\n\nSelect an option to run:\n\n" +
    "1) Movie ratings\n" +
    "2) Average of friends per age\n" +
    "3) Min/Max temperatures\n" +
    "4) Count of words occurrences\n" +
    "5) Spending Amount per customer\n" +
    "6) Popularity of movies by ratings, show id's \n" +
    "7) Popularity of movies by ratings, show titles\n" +
    "q) Exit\n"

  /**
    *
    * @param args to pass to main driver class
    */
  def main(args: Array[String]): Unit = {
    menu(readLine(menuOptions))
  }

  /**
    *
    * @param input
    */
  def menu(input: String): Unit = {

    (input: @switch) match {

      case "q" => {
        println("Quitting application....")
        System.exit(1)
      }

      case "1" => {
        println("---------------------------------------<[ Movie ratings ]>-----------------------------------------")
        println(s"Get the distribution of ratings for dataset: $movieLensIdsDataSetPath")
        val distributionOfRatings = movieRecommendationService.getMovieRatingsCount(movieLensIdsDataSetPath)
        println("|rating, count of rating|")
        distributionOfRatings.foreach(rating => println(rating))
      }

      case "2" => {
        println("---------------------------------------<[ Average of friends per age ]>----------------------------")
        println(s"Get the averages of friends by ages for dataset: $fakeFriendsDataSetPath")
        val avergesOfFriendsByAge = averagesCounter.getSortedAveragesByAge(fakeFriendsDataSetPath)
        println("|age, average of friends|")
        avergesOfFriendsByAge.sorted.foreach(average => println(average))
      }

      case "3" => {
        println("---------------------------------------<[ Min/Max temperatures ]>----------------------------------")
        println(s"Get MINIMUM of temperatures for dataset: $temperaturesDataSetPath")
        val minimaTemperatures = temperatureFilter.getTemperaturesForStations(
          temperaturesDataSetPath,
          FilterType.MIN_TEMPERATURE
        )
        println("|stationId, minTemp F|")
        minimaTemperatures.sorted.foreach(result => {
          println(result)
        })

        println("-------------------------")

        println(s"Get MAXIMUM of temperatures for dataset: $temperaturesDataSetPath")
        val maximaTemperatures = temperatureFilter.getTemperaturesForStations(
          temperaturesDataSetPath,
          FilterType.MAX_TEMPERATURE
        )
        println("|stationId, maxTemp F|")
        maximaTemperatures.sorted.foreach(result => {
          println(result)
        })
      }

      case "4" => {
        println("---------------------------------------<[ Count of words occurrences ]>----------------------------")
        println(s"Get the count of words for dataset: $bookDataSetPath")
        val wordCounts = wordCounter.getCountOfEachWord(bookDataSetPath)
        println("|count, word|")
        wordCounts.foreach(result => println(result))
      }

      case "5" => {
        println("---------------------------------------<[ Spending Amount per customer ]>--------------------------")
        println(s"Get spending amount per customer for dataset: $customerOrderDataset")
        val spendings = spendingCounter.getSpendingAmountPerCustomer(customerOrderDataset)
        println("|amount, customerId|")
        spendings.foreach(result => println(result))
      }

      case "6" => {
        println("---------------------------------------<[ Popularity of movies ]>----------------------------------")
        val movieOccurences = movieRecommendationService.getSortedMoviesByCount(movieLensIdsDataSetPath)
        println("|count, movieId|")
        movieOccurences.foreach(result => println(result))
      }

      case "7" => {
        println("---------------------------------------<[ Popularity of movies by ratings ]>---------------")
        val top10MovieTitles = movieRecommendationService.mapMovieIdsToTitles(
          movieLensIdsDataSetPath,
          movieLensTitlesDataSetPath)

        println("|movieRating, movieTitle|")
        top10MovieTitles.foreach(x => println(x))
      }

      case default => {
        println("Not a valid option")
        menu(readLine(menuOptions))
        return
      }

    }

    menu(readLine(menuOptions))
  }

}