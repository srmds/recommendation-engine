package nl.jongensvantechniek.movierecommendations.application

import org.apache.log4j.{Level, LogManager, Logger}
import org.apache.spark.SparkContext
import org.apache.spark.sql.{DataFrameReader, SparkSession}

/**
  *
  */
object SparkManager {

  val log: Logger = LogManager.getRootLogger
  var appName: String = _
  var sc: SparkContext = _
  var spark: SparkSession = _

  /**
    * Initialize spark session, context and logger.
    * @param name
    */
  def init(name: String): Unit = {
    appName = name

    //Create the spark session and the spark context to be used as the main context.
    spark = createSparkSession(appName)
    sc = spark.sparkContext

    // Set log levels
    sc.setLogLevel("ERROR")
    Logger.getLogger("org").setLevel(Level.ERROR)
    Logger.getLogger("akka").setLevel(Level.ERROR)
    log.setLevel(Level.DEBUG)

    //Log current spark version
    val sparkVersion = spark.version
    log.info(s"Spark version: $sparkVersion")
  }

  /**
    *
    *  sets up the main Dataframe reader and returns the main predefined reder.
    * @return
    */
  protected def reader: DataFrameReader = spark.read
    .option("header",value = true)
    .option("inferSchema", value = true)
    .option("mode", "DROPMALFORMED")

  /**
    *
    * @return
    */
  protected def readerWithoutHeader: DataFrameReader = spark.read
    .option("header", value = true)
    .option("inferSchema", value = true)
    .option("mode", "DROPMALFORMED")

  /**
    * Create or get the initialized context reader.
    * @param appName
    * @return
    */
  protected def createSparkSession(appName: String): SparkSession = {
    SparkSession.builder()
      .appName(appName)
      .master("local[*]")
      .config("option", "some-value")

      .getOrCreate()
  }

  /**
    *
    */
  def close(): Unit = {
    spark.close()
  }
}
