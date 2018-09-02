package nl.jongensvantechniek.movierecommendations.service.movie

import nl.jongensvantechniek.movierecommendations.application.SparkManager

/**
  *
  */
object MovieRecommendationService extends MovieRatingsCounter
                                    with MovieCounter
                                    with MovieTitleLoader
                                    with MovieIdLoader {

  private val sparkManager = SparkManager

  /**
    *
    * @param dataSourceMovieIdsPath
    * @param dataSourceMovieTitlesPath
    * @return
    */
  def mapMovieIdsToTitles(dataSourceMovieIdsPath: String,
                          dataSourceMovieTitlesPath: String): Seq[(Int, String)] = {
    sparkManager.init("Popularity of movies by ratings")
    val movieTitles = loadMovieTitles(dataSourceMovieTitlesPath)
    val sortedMovieIdsByRatingsCount = loadMovieIds(sparkManager.sc, dataSourceMovieIdsPath)

    // Create a broadcast variable of our ID -> movie titles map
    val movieTitlesMap = sparkManager.sc.broadcast(movieTitles)

    val result = sortedMovieIdsByRatingsCount.map(x => (x._1, movieTitlesMap.value(x._2))).sortByKey(false).collect()
    sparkManager.close()

    result
  }

  /***
    *
    * @param dataSourcePath
    * @param sorted
    * @return
    */
  def getMovieRatingsCount(dataSourcePath: String, sorted: Boolean = true): Seq[(String, Long)] = {
    sparkManager.init("Movie ratings")
    val result = getSortedRatingsCount(sparkManager.sc, dataSourcePath)
    sparkManager.close()

    result
  }

  /**
    *
    * @param dataSourcePath
    * @return
    */
  def getSortedMoviesByCount(dataSourcePath: String): Seq[(Int, Int)] = {
    sparkManager.init("Popularity of movies")
    val result = getSortedMoviesCount(sparkManager.sc, dataSourcePath)
    sparkManager.close()

    result
  }
}