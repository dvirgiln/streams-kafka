package com.david.ts.consumer

import org.apache.log4j.Logger
import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.spark.streaming.StreamingContext._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
object ConsumerMain extends App {
  lazy val logger = Logger.getLogger(getClass)
  logger.info(s"Starting Main")
  val kafkaEndpoint = args(0)
  logger.info(s"Connecting to kafka endpoint $kafkaEndpoint")
  val conf = new SparkConf().setAppName("Consumer")
  val ssc = new StreamingContext(conf, Seconds(1))

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

  val topics = Array("test")
  val stream = KafkaUtils.createDirectStream[String, String](
    ssc,
    PreferConsistent,
    Subscribe[String, String](topics, kafkaParams)
  )

  val values = stream.map(record => record.value)
  values.print()
  logger.info(s"Starting Consumer")
  ssc.start()
  ssc.awaitTermination()
  logger.info(s"End Consumer")
}
