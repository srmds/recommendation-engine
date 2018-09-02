package nl.jongensvantechniek.movierecommendations.exploration.spent

import nl.jongensvantechniek.movierecommendations.application.SparkManager

/**
  *
  */
object SpendingCounter {

  private val sparkManager = SparkManager

  /**
    *
    * @param dataSourcePath
    * @return
    */
  def getSpendingAmountPerCustomer(dataSourcePath: String): Seq[(Float, Int)] =  {
    sparkManager.init("Spending Amount per customer")
    // Load up each line of the ratings data into an RDD
    val lines = sparkManager.sc.textFile(dataSourcePath)

    // Split by commas
    // Extract the customerId, amount spend
    // and create a tuple that is our result
    val rdd = lines.map((lineItem) => {
      def parseLine(line:String) = {
        val fields = line.split(",")
        val customerId = fields(0).toInt
        val amountSpent = fields(2).toFloat

        (customerId, amountSpent)
      }

      parseLine(lineItem)
    })

    // Reduce (customerId, amountSpent) by stationId retaining the minimum temperature found
    val customersSpendAmount = rdd.reduceByKey( (x,y) => x + y)

    // Return an RDD sorted by amounts spent count, instead of the customer Id : (99.99, 123) instead of (123, 99.99)
    // Collect the results from the RDD (This kicks off computing the DAG and actually executes the job)
    val result = customersSpendAmount.map(x => (x._2, x._1)).sortByKey().collect()
    sparkManager.close()

    result
  }
}
