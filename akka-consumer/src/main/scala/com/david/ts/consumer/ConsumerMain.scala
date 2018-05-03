package com.david.ts.consumer

import java.util.concurrent.atomic.AtomicLong

import akka.kafka.scaladsl.Consumer
import akka.kafka.{ ConsumerSettings, Subscriptions }
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Sink
import org.apache.kafka.clients.consumer.{ ConsumerConfig, ConsumerRecord }
import org.apache.kafka.common.serialization.{ ByteArrayDeserializer, StringDeserializer }
import org.apache.log4j.Logger

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
object ConsumerMain extends App {
  import akka.actor._
  logger.info(s"Starting Consumer")
  implicit val system = ActorSystem()
  implicit val mater = ActorMaterializer()

  val kafkaEndpoint = System.getProperty("kafka_endpoint")
  logger.info(s"Connecting to kafka endpoint $kafkaEndpoint")
  val consumerSettings = ConsumerSettings(system, new ByteArrayDeserializer, new StringDeserializer)
    .withBootstrapServers(kafkaEndpoint)
    .withGroupId("group1")
    .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")

  lazy val logger = Logger.getLogger(getClass)
  /*val done =
    Consumer.committableSource(consumerSettings, Subscriptions.topics("test"))
      .mapAsync(1) { msg =>
        val value = msg.record.value
        logger.info(value)
        Future(msg)
      }
      .mapAsync(1) { msg =>
        msg.committableOffset.commitScaladsl()
      }
      .runWith(Sink.ignore)*/

  Consumer.plainSource(consumerSettings, Subscriptions.topics("test")).mapAsync(1) { msg =>
    val value = msg.value
    logger.info(value)
    Future(msg)
  }.runWith(Sink.ignore)
}
