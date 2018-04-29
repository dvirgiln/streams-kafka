package com.david.ts.producer

import akka.NotUsed
import akka.kafka.ProducerSettings
import akka.kafka.scaladsl.Producer
import akka.stream._
import akka.stream.scaladsl.{ GraphDSL, RunnableGraph, Sink, Source, Zip }
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.{ ByteArraySerializer, StringSerializer }
import org.apache.log4j.Logger

import scala.concurrent.Future
import scala.concurrent.duration._
import scala.util.Random
import scala.concurrent.ExecutionContext.Implicits.global

object ProducerMain extends App {
  import akka.actor._
  logger.info(s"Starting Producer")
  lazy val logger = Logger.getLogger(getClass)
  implicit val system = ActorSystem()
  implicit val mater = ActorMaterializer()
  val producerSettings = ProducerSettings(system, new ByteArraySerializer, new StringSerializer)
    .withBootstrapServers("192.168.99.100:9092")
  val random = new Random()
  val s = Source
    .tick(0.seconds, 300.millis, "").limit(200).map(_ => random.nextInt(1000))

  s.map { number =>
    logger.info(s"Producing record: $number")
    new ProducerRecord[Array[Byte], String]("test", s"$number")
  }.runWith(Producer.plainSink(producerSettings))
}
