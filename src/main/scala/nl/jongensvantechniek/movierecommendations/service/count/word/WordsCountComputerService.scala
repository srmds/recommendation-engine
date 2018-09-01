package nl.jongensvantechniek.movierecommendations.service.count.word

import org.apache.spark.SparkContext

object WordsCountComputerService extends WordCounter {
  /**
    *
    * @inheritdoc
    */
  def computeWordCount(sparkContext: SparkContext,
                       dataSourcePath:String = "datasets/book/book.txt"):  Seq[(Int, String)] = {
    getCountOfEachWord(sparkContext, dataSourcePath)
  }
}
