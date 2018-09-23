package nl.jongensvantechniek.movierecommendations.service.social

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

trait GraphLoader {

  /**
    * @param sc
    * @param dataSourcePath
    * @return
    */
  protected def loadGraph(sc: SparkContext, dataSourcePath: String): RDD[String] = {
    sc.textFile(dataSourcePath)
  }

}
