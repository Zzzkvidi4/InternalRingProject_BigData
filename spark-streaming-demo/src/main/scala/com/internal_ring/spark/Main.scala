package com.internal_ring.spark


import java.util.Date

import com.mongodb.client.MongoCollection
import com.mongodb.spark.MongoSpark
import com.mongodb.spark.config.{MongoCollectionConfig, WriteConfig}
import com.mongodb.util.JSON
import kafka.serializer.StringDecoder
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{SQLContext, SparkSession}
import org.apache.spark.streaming.{Minutes, Seconds, StreamingContext}
import org.apache.spark.streaming.kafka.KafkaUtils
import org.bson.Document

object Main {
  val PACKAGE_CREATION_DURATION = Seconds(30)
  val ANALITICS_CREATION_DURATION = PACKAGE_CREATION_DURATION * 2
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("TestSparkStreaming").setMaster("local[2]")
      .set("spark.mongodb.input.uri", "mongodb://127.0.0.1/")
      .set("spark.mongodb.output.uri", "mongodb://127.0.0.1/")
      .set("spark.mongodb.input.database", "BigData")
      .set("spark.mongodb.output.database", "BigData")
      .set("spark.mongodb.output.collection", "myCollection")
    val sparkStreamingContext = new StreamingContext(sparkConf, PACKAGE_CREATION_DURATION)
    sparkStreamingContext.checkpoint("file:///checkpoints")
    val sparkSession = SparkSession
      .builder()
      .appName("sparkSession")
      .config("spark.sql.warehouse.dir", "file:///D:\\Work\\BigData\\")
      .getOrCreate()
    val topics = "test".split(",").toSet
    val kafkaParams = Map[String, String]("metadata.broker.list" -> "localhost:9092")
    val messages = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](sparkStreamingContext, kafkaParams, topics)
    case class Review(hotel_id: Long, message: String, service: Byte, comfort: Byte, price: Byte, distance_from_airport: Byte)
    val lines = messages.map(_._2)

    //calculating average score per ANALITICS_CREATION_DURATION
    //parsing every review to average score
    val averageScore = lines.map(line => {
      val res = Document.parse(line)
      print(res)
      val flag = res.containsKey("hotel_id")
      val hotel: Integer = res.getInteger("hotel_id")
      val hotel_id = res.getInteger("hotel_id").longValue()
      val score =
        (res.getInteger("service").intValue() +
          res.getInteger("comfort").intValue() +
          res.getInteger("price").intValue() +
          res.getInteger("distance_from_airport").intValue()).toDouble / 4
      (hotel_id, score)
    })
    val windowedResults = averageScore.window(ANALITICS_CREATION_DURATION, ANALITICS_CREATION_DURATION)

    //writing configuration
    val writeConfigReviews = WriteConfig(Map("collection" -> "reviews", "writeConcern.w" -> "majority"), Some(WriteConfig(sparkConf)))
    val writeConfigAveragePerWeek = WriteConfig(Map("collection" -> "average_marks", "writeConcern.w" -> "majority"), Some(WriteConfig(sparkConf)))

    //calculating average marks
    val sumMarks = windowedResults.reduceByKey((v1, v2) => v1 + v2)
    val countById = windowedResults.map{
      case (id: Long, _: Double) => (id, 1L)
    }.reduceByKey((count1, count2) => count1 + count2)
    val averageMarks = sumMarks.join(countById).map{
      case (id: Long, (sum: Double, count: Long)) => (id, sum / count)
    }
    averageMarks.print()
    val versions = averageMarks.updateStateByKey(updateRunningCount)
    //save to db
    averageMarks.join(versions).foreachRDD(rdd => {
      val documents = rdd.map{
        case (hotel_id: Long, (average_mark: Double, version: Long)) =>
          val date = new Date()
          new Document().append("hotel_id", hotel_id).append("average_mark", average_mark).append("version", version).append("date", date)
      }
      MongoSpark.save(documents, writeConfigAveragePerWeek)
    })

    //throw collected data to mongo
    lines.foreachRDD(rdd => {
      val messages = sparkSession.read.json(rdd)
      messages.show()
      val reviews = messages.rdd.map(row => {
        val hotel_id = row.getAs[Long]("hotel_id")
        val message = row.getAs[String]("message")
        val service = row.getAs[Long]("service").toByte
        val comfort = row.getAs[Long]("comfort").toByte
        val price = row.getAs[Long]("price").toByte
        val distance_from_airport = row.getAs[Long]("distance_from_airport").toByte
        Review(hotel_id, message, service, comfort, price, distance_from_airport)
      })
      reviews.foreach(review => print(review))
      val documents = rdd.map(line => new Document().append("review", line))
      MongoSpark.save(documents, writeConfigReviews)
    })
    sparkStreamingContext.start()
    sparkStreamingContext.awaitTermination()
  }

  def updateRunningCount(values: Seq[Double], state:Option[Long]):Some[Long] = {
    Some(state.getOrElse(0L) + 1)
  }
}
