package nl.jongensvantechniek.movierecommendations.service.movie

import org.apache.log4j.{LogManager, Logger}
import org.apache.spark.SparkContext

/**
  * Count how many of each star rating exists in the MovieLens 100K data seet.
  */
trait MovieRatingsCounter {

  val log: Logger = LogManager.getRootLogger

  /**
    *  Read in the dataset and compute the distribution of ratings, and sort the result by ratings.
    *
    *  Input File format:
    *
    *     | userID  movieID  rating  timestamp (UTC epoch time since 01-01-1970) |
    *
    *  Example:
    *
    *     | 196 242 3  881250949 |
    *
    *  Output distribution of ratings:
    *
    *     | rating, count(rating) |
    *
    * @param sparkContext the context in which the job will run
    * @param dataSourcePath the dataset for which the distribution is computed
    * @return a collection, sorted by rating and its count per rating
    */
  def getSortedRatingsCount(sparkContext: SparkContext, dataSourcePath: String): Seq[(String, Long)] = {
    // Load up each line of the ratings data into an RDD
    val lines = sparkContext.textFile(dataSourcePath)

    // Convert each line to a string, split it out by tabs, and extract the third field (rating), the ratings will
    // be outputted as a new RDD
    val ratings = lines.map(x => x.toString.split("\t")(2))

    // Count up how many times each value (rating) occurs
    // The countByValue() is on a RDD is a Spark action, calling this will cause spark, to compute it's DAG and start distributing
    // and processing all the data in a distributed fashion (on clusters)
    val results = ratings.countByValue()

    // Sort the resulting map of (rating, count) tuples and return the tuples collection
    results.toSeq.sortBy(_._1)
  }
}
