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
import org.apache.spark.streaming.{Milliseconds, Minutes, Seconds, StreamingContext}
import org.apache.spark.streaming.kafka.KafkaUtils
import org.bson.Document

object Main {
  val PACKAGE_CREATION_DURATION = org.apache.spark.streaming.Seconds(1)
  val ANALITICS_CREATION_DURATION = Minutes(2)
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
      .config("spark.sql.warehouse.dir", "file:///warehouse")
      .getOrCreate()
    val topics = "test".split(",").toSet
    val kafkaParams = Map[String, String]("metadata.broker.list" -> "localhost:9092")
    val messages = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](sparkStreamingContext, kafkaParams, topics)

    val lines = messages.map(_._2)

    //calculating average score per ANALITICS_CREATION_DURATION
    //parsing every review to average score
    val averageScore = lines.map(line => {
      val res = Document.parse(line)
      print(res)
      val hotel_id = res.getInteger("hotelId").longValue()
      val score =
        (res.getInteger("service").intValue() +
          res.getInteger("comfort").intValue() +
          res.getInteger("price").intValue() +
          res.getInteger("distanceFromAirport").intValue()).toDouble / 4
      (hotel_id, score)
    })
    val windowedResults = averageScore.window(ANALITICS_CREATION_DURATION, ANALITICS_CREATION_DURATION)

    //writing configuration
    val writeConfigReviews = WriteConfig(Map("collection" -> "reviews", "writeConcern.w" -> "majority"), Some(WriteConfig(sparkConf)))
    val writeConfigAveragePerWeek = WriteConfig(Map("collection" -> "averageMarks", "writeConcern.w" -> "majority"), Some(WriteConfig(sparkConf)))

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
          new Document().append("hotelId", hotel_id).append("averageMark", average_mark).append("version", version).append("date", date)
      }
      MongoSpark.save(documents, writeConfigAveragePerWeek)
    })

    //throw collected data to mongo
    lines.foreachRDD(rdd => {
      val messages = sparkSession.read.json(rdd)
      //messages.show()
      val reviews = messages.rdd.map(row => {
        new Review(row)
      }).map(review =>
        review.toDocument
      )
      //reviews.foreach(review => print(review))
      MongoSpark.save(reviews, writeConfigReviews)
    })
    sparkStreamingContext.start()
    sparkStreamingContext.awaitTermination()
  }

  def updateRunningCount(values: Seq[Double], state:Option[Long]):Some[Long] = {
    Some(state.getOrElse(0L) + 1)
  }
}
