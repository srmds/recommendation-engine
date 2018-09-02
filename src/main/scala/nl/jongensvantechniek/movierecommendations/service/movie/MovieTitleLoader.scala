package nl.jongensvantechniek.movierecommendations.service.movie

import java.nio.charset.CodingErrorAction
import scala.io.{Codec, Source}

/**
  *
  */
trait MovieTitleLoader {

  /**
    *
    * @param dataSourcePath
    * @return
    */
  protected def loadMovieTitles(dataSourcePath: String): Map[Int, String] = {

    implicit val codec = Codec("UTF-8")
    codec.onMalformedInput(CodingErrorAction.REPLACE)
    codec.onUnmappableCharacter(CodingErrorAction.REPLACE)

    // Load up each line of the ratings data into an RDD
    val lines = Source.fromFile(dataSourcePath).getLines()
    var movieTitles = Map[Int,String]()

    // Split by pipe. Extract the movieTitles and create a tuple that is our result
    for (line <- lines) {
      val fields = line.split('|')

      if (fields.length > 1) {
        // (movieId, movieTitle)
        movieTitles += fields(0).toInt -> fields(1)
      }
    }

    movieTitles
  }

}
