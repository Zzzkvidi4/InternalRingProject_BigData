package com.internal_ring.spark

import java.util

import com.mongodb.spark.MongoSpark
import com.mongodb.spark.config.WriteConfig
import kafka.serializer.StringDecoder
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{SQLContext, SparkSession}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.kafka.KafkaUtils
import org.bson.Document

import scala.collection.mutable

object Main {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("TestSparkStreaming").setMaster("local[2]")
      .set("spark.mongodb.input.uri", "mongodb://127.0.0.1/")
      .set("spark.mongodb.output.uri", "mongodb://127.0.0.1/")
      .set("spark.mongodb.input.database", "test")
      .set("spark.mongodb.output.database", "test")
      .set("spark.mongodb.output.collection", "myCollection")
    val sparkStreamingContext = new StreamingContext(sparkConf, Seconds(30))
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
    lines.print()

    val writeConfig = WriteConfig(Map("collection" -> "reviews", "writeConcern.w" -> "majority"), Some(WriteConfig(sparkConf)))
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
      MongoSpark.save(documents, writeConfig)
    })
    sparkStreamingContext.start()
    sparkStreamingContext.awaitTermination()
  }
}
