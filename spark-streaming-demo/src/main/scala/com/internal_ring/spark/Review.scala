package com.internal_ring.spark

import org.apache.spark.sql.Row
import org.bson.Document

class Review(var hotel_id: Long, var message: String, var service: Byte, var comfort: Byte, var price: Byte, var distance_from_airport: Byte) {
  def this(row: Row){
    this(
      row.getAs[Long]("hotelId"),
      row.getAs[String]("message"),
      row.getAs[Long]("service").toByte,
      row.getAs[Long]("comfort").toByte,
      row.getAs[Long]("price").toByte,
      row.getAs[Long]("distanceFromAirport").toByte
    )
  }

  def toDocument: Document = {
    new Document()
      .append("hotelId", hotel_id)
      .append("message", message)
      .append("service", service)
      .append("comfort", comfort)
      .append("price", price)
      .append("distanceFromAirport", distance_from_airport)
  }
}
