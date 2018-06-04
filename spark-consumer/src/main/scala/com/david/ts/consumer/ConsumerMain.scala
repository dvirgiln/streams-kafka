package com.david.ts.consumer

import org.apache.log4j.Logger
import org.apache.spark._
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.stat.Statistics
import org.apache.spark.streaming._
import org.apache.spark.streaming.StreamingContext._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object ConsumerMain extends App {
  Thread.sleep(20000)
  lazy val logger = Logger.getLogger(getClass)
  logger.info(s"Starting Main")
  val kafkaEndpoint = args(0)
  logger.info(s"Connecting to kafka endpoint $kafkaEndpoint")
  val conf = new SparkConf().setAppName("Consumer")
  val ssc = new StreamingContext(conf, Seconds(5))
  ssc.sparkContext.setLogLevel("WARN")

  import org.apache.kafka.clients.consumer.ConsumerRecord
  import org.apache.kafka.common.serialization.StringDeserializer
  import org.apache.spark.streaming.kafka010._
  import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
  import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe

  val kafkaParams = Map[String, Object](
    "bootstrap.servers" -> kafkaEndpoint,
    "key.deserializer" -> classOf[StringDeserializer],
    "value.deserializer" -> classOf[StringDeserializer],
    "group.id" -> "group_2",
    "auto.offset.reset" -> "latest",
    "enable.auto.commit" -> (false: java.lang.Boolean)
  )
  val topics = List(Array("shop_product_features_thirty_seconds"), Array("shop_features_ten_sec"))
  topics.foreach { topic =>
    val stream = KafkaUtils.createDirectStream[String, String](
      ssc,
      PreferConsistent,
      Subscribe[String, String](topic, kafkaParams)
    )
    val values = stream.map(record => record.value).map(a => a.split(",")).
      map(value => (value(0).toInt, Vectors.dense(value.tail.map(_.toDouble)))).
      groupByKeyAndWindow(org.apache.spark.streaming.Duration(20000))
    values.print()
    values.foreachRDD { rdd =>
      val ids = rdd.keys.collect()
      val rddById = ids.toSeq.map(id => (id -> rdd.filter(_._1 == id).map(_._2).flatMap(a => a)))
      val a = rddById.foreach {
        case (id, vectorRDD) =>
          val sample = vectorRDD.collect()
          //println(s"ID222: $id vectors:${sample.size}")
          println()
          val stats = Statistics.colStats(vectorRDD)
          println(s"Accumulated stats id=$id mean: ${stats.mean} count: ${stats.count} variance: ${stats.variance}")
      }
      /*rddById.foreach {
        case (id, vectorRDD) =>
          println()
          val stats = Statistics.colStats(vectorRDD)
          println(s"Accumulated stats id=$id mean: ${stats.mean} count: ${stats.count} variance: ${stats.variance}")
      }*/
    }

  }

  logger.info(s"Starting Consumer")
  ssc.start()
  ssc.awaitTermination()
  logger.info(s"End Consumer")
}
