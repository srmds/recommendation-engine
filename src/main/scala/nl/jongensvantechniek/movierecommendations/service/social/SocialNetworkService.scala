package nl.jongensvantechniek.movierecommendations.service.social

import nl.jongensvantechniek.movierecommendations.application.SparkManager
import org.apache.spark.rdd.RDD

/**
  *
  */
object SocialNetworkService extends SuperHeroNamesLoader
  with OccurenceCounter
  with GraphLoader {

  private val sparkManager = SparkManager

  /**
    * @param dataSourceGraphPath graph input file
    * @param dataSourceNamesPath names input file
    * @return result map id -> (score, name)
    */
  def getPopulairHeroes(dataSourceGraphPath: String,
                        dataSourceNamesPath: String): Map[Int, (Int, String)] = {
    sparkManager.init("Most Popular Superheros")

    // Build up a hero ID -> name RDD
    val nameLines: RDD[String] = loadNames(sparkManager.sc, dataSourceNamesPath)
    val names = nameLines.flatMap(parseNames)

    // Load up the superhero co-appearance data
    val graph = loadGraph(sparkManager.sc, dataSourceGraphPath)

    // Convert to (heroID, number of connections) RDD
    val pairings = graph.map(countCoOccurences)

    // Combine entries that span more than one line
    val totalFriendsByCharacter = pairings.reduceByKey((x, y) => x + y)

    // Flip it to # of connections, hero ID
    val flipped = totalFriendsByCharacter.map(x => (x._2, x._1)).collect()

    var result = Map[Int, (Int, String)]()

    // Broadcast names so it can be used to map graph user id to name
    val broadcastedNames = sparkManager.sc.broadcast(names.collect())

    for (heroScoreItem <- flipped) {
      val heroScore = heroScoreItem._1
      val heroId = heroScoreItem._2
      val matchingName = broadcastedNames.value.filter(x => x._1 == heroId)

      if (matchingName.nonEmpty) {
        val heroName = matchingName(0)._2
        result += heroScore -> (heroId, heroName)
      }
    }

    result
  }

}
