package nl.jongensvantechniek.movierecommendations

/**
  *
  */
object Main extends InitSpark {

  /**
    *
    * @param args to pass to main driver class
    */
  def main(args: Array[String]): Unit = {

    val version = spark.version
    val dataSetPath = "datasets/movielens/ml-100k/u.data"

    log.info(s"Spark version: $version")
    log.debug(s"Using dataset: $dataSetPath")

    close()
  }
}