package nl.jongensvantechniek.movierecommendations.service.movie

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

/**
  *
  */
trait MovieIdLoader extends Serializable {


  /**
    *
    * @param sc
    * @param dataSourcePath
    * @return
    */
  protected def loadMovieIds(sc: SparkContext, dataSourcePath: String): RDD[(Int, Int)] = {
    // Load up each line of the ratings data into an RDD
    val lines = sc.textFile(dataSourcePath)

    // Convert each line to a string, split it out by tabs, and extract the third field (rating), the ratings will
    // be outputted as a new RDD
    val movies = lines.map(x => (x.split("\t")(1).toInt, 1))

    val movieCounts = movies.reduceByKey((x,y) => x + y)

    //map count to id (ratingCount,movieId) instead of (movieId, ratingCount)
    movieCounts.map(x => (x._2, x._1)).sortByKey()
  }

}
