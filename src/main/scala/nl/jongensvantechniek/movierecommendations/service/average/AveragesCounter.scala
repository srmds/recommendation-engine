package nl.jongensvantechniek.movierecommendations.service.average

import org.apache.spark.SparkContext

/**
  *
  */
trait AveragesCounter extends Serializable {

  /**
    *
    * @param sparkContext
    * @param dataSourcePath
    * @return
    */
  def getSortedAveragesByAge(sparkContext: SparkContext, dataSourcePath: String): Array[(Int, Int)] = {

    // Load each line of the source data into an RDD
    val lines = sparkContext.textFile(dataSourcePath)

    // Split by commas
    // Extract the age and numFriends fields, and convert to integers
    // and create a tuple that is our result
    val rdd = lines.map((lineItem) => {
      def parseLine(line:String) = {
        val fields = line.split(",")
        (fields(2).toInt, fields(3).toInt)
      }

      parseLine(lineItem)
    })

    val totalsByAge = rdd.mapValues(x => (x, 1)).reduceByKey( (x,y) => (x._1 + y._1, x._2 + y._2))

    // So now we have tuples of (age, (totalFriends, totalInstances))
    // To compute the average we divide totalFriends / totalInstances for each age.
    val averagesByAge = totalsByAge.mapValues(x => x._1 / x._2)

    // Collect the results from the RDD (This kicks off computing the DAG and actually executes the job)
    averagesByAge.collect()
  }

}
