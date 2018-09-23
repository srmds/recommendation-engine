package nl.jongensvantechniek.movierecommendations.service.social

import org.apache.log4j.{LogManager, Logger}
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

/**
  *
  */
trait SuperHeroNamesLoader {

  val log: Logger = LogManager.getRootLogger

  /**
    * @param dataSourcePath
    * @return
    */
  protected def loadNames(sc: SparkContext, dataSourcePath: String): RDD[String] = {

    // Load up each line of the ratings data into an RDD
    // Build up a hero ID -> name RDD
    sc.textFile(dataSourcePath)
  }

  /**
    * Function to extract hero ID -> hero name tuples (or None in case of failure)
    * @param line
    * @return
    */
  def parseNames(line: String) : Option[(Int, String)] = {
    val fields = line.split('\"')

    if (fields.length > 1) {
       return Some(fields(0).trim().toInt, fields(1))
    }

    return None
  }

}