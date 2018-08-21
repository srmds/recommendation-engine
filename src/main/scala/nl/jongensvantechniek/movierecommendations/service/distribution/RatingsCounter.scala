package nl.jongensvantechniek.movierecommendations.service.distribution

import org.apache.spark.SparkContext

trait RatingsCounter {

  /**
    *  Read in the dataset and compute the distribution of ratings, and sort the result by ratings.
    *
    * @param sparkContext the context in which the job will run
    * @param dataSourcePath the dataset for which the distribution is computed
    * @return a collection, sorted by rating and its count per rating
    */
  def getSortedRatingsCount(sparkContext: SparkContext, dataSourcePath: String): Seq[(String, Long)] = {
    // Load up each line of the ratings data into an RDD
    val lines = sparkContext.textFile(dataSourcePath)

    // Convert each line to a string, split it out by tabs, and extract the third field.
    // (The file format is userID, movieID, rating, timestamp)
    val ratings = lines.map(x => x.toString.split("\t")(2))

    // Count up how many times each value (rating) occurs
    val results = ratings.countByValue()

    // Sort the resulting map of (rating, count) tuples
    results.toSeq.sortBy(_._1)
  }
}
