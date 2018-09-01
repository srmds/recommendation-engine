package nl.jongensvantechniek.movierecommendations.service.count.spent

import org.apache.spark.SparkContext

object SpendingAmountComputerService extends SpendingCounter {

  /**
    *
    * @inheritdoc
    */
  def computSpendingCount(sparkContext: SparkContext,
                       dataSourcePath:String = "datasets/spending/customer_orders.csv"):  Seq[(Float, Int)] = {
    getSpendingAmountPerCustomer(sparkContext, dataSourcePath)
  }
}
