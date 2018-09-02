package nl.jongensvantechniek.movierecommendations.service.movie

import org.apache.spark.SparkContext

/**
  *
  */
trait MovieCounter  {

  /**
    *
    * @param sc
    * @param dataSourcePath
    * @return
    */
  protected def getSortedMoviesCount(sc: SparkContext, dataSourcePath: String): Seq[(Int, Int)] = {
    // Load up each line of the ratings data into an RDD
    val lines = sc.textFile(dataSourcePath)

    // Convert each line to a string, split it out by tabs, and extract the third field (rating), the ratings will
    // be outputted as a new RDD
    val movies = lines.map(x => (x.split("\t")(1).toInt, 1))

    // Return an RDD sorted by most popular movie(movieId, count) s
    // instead of (movieId, countOfOccurences)
    // Collect the results from the RDD (This kicks off computing the DAG and actually executes the job)
    movies.reduceByKey((x, y) => x + y).map(x => (x._2, x._1)).sortByKey().collect()
  }
}
