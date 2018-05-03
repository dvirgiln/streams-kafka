package com.david.ts.consumer

import org.apache.log4j.Logger

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
object ConsumerMain extends App {
  lazy val logger = Logger.getLogger(getClass)
  logger.info(s"Starting Consumer")
  val kafkaEndpoint = System.getProperty("kafka_endpoint")
  logger.info(s"Connecting to kafka endpoint $kafkaEndpoint")

}
