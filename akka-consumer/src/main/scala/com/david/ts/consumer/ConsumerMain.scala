package com.david.ts.consumer

import java.util.concurrent.atomic.AtomicLong

import akka.NotUsed
import akka.kafka.scaladsl.{ Consumer, Producer }
import akka.kafka.{ ConsumerSettings, ProducerSettings, Subscriptions }
import akka.stream._
import akka.stream.scaladsl.{ Broadcast, Flow, GraphDSL, Merge, RunnableGraph, Sink, Source }
import com.david.ts.domain.Configs
import com.david.ts.domain.Domain.{ Config, SalesRecord }
import org.apache.kafka.clients.consumer.{ ConsumerConfig, ConsumerRecord }
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.{ ByteArrayDeserializer, ByteArraySerializer, StringDeserializer, StringSerializer }
import org.apache.log4j.Logger

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.FiniteDuration
import scala.concurrent.duration._

object ConsumerMain extends App {

  val configs: Seq[Config] = Configs.configs
  import com.david.ts.utils.SerializationUtils._
  import akka.actor._
  lazy val logger = Logger.getLogger(getClass)
  logger.info(s"Starting Consumer")
  implicit val system = ActorSystem()
  implicit val mater = ActorMaterializer()

  val kafkaEndpoint = System.getProperty("kafka_endpoint", "localhost:9092")
  logger.info(s"Connecting to kafka endpoint $kafkaEndpoint")
  val consumerSettings = ConsumerSettings(system, new ByteArrayDeserializer, new ByteArrayDeserializer)
    .withBootstrapServers(kafkaEndpoint)
    .withGroupId("group1")
    .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")

  val source = Consumer.plainSource(consumerSettings, Subscriptions.topics("shops_records")).mapAsync(1) { msg =>
    val value = deserialise(msg.value).asInstanceOf[SalesRecord]
    Future(value)
  }

  /*val source = Source
    .tick(0.seconds, 5.seconds, "").map(_ => SalesRecord(System.currentTimeMillis(),1, 1, 1, 25d))*/

  val producerSettings = ProducerSettings(system, new ByteArraySerializer, new StringSerializer)
    .withBootstrapServers(kafkaEndpoint).withCloseTimeout(5 minutes)

  val subFlow = source.statefulMapConcat { () =>
    val windows = configs
    logger.info(s"Frequencies: $windows")
    val generator = new WindowCommandGenerator(windows.map(_.frequency))

    event =>
      val timestamp = event.transactionTimestamp
      generator.forEvent(timestamp, event)
  }.groupBy(64, command => command.w)

  val graph = GraphDSL.create() { implicit builder: GraphDSL.Builder[NotUsed] =>
    import GraphDSL.Implicits._
    val in = Inlet[WindowCommand]("Propagate.in")
    val bcast = builder.add(Broadcast[WindowCommand](configs.length))
    val merge = builder.add(Merge[ProducerRecord[Array[Byte], String]](configs.length))
    configs.map { config =>
      val filter = Flow[WindowCommand].filter(_.w.duration.get == config.frequency.toMillis)
      val commands = Flow[WindowCommand].takeWhile(!_.isInstanceOf[CloseWindow])

      val aggregator = Flow[WindowCommand].fold(List[SalesRecord]()) {
        case (agg, OpenWindow(window)) => agg
        case (agg, CloseWindow(_)) => agg
        case (agg, AddToWindow(ev, _)) => ev :: agg
      }

      val flatten = Flow[List[SalesRecord]].mapConcat(a => a)

      val groupedBySplit = Flow[SalesRecord].fold(Map[String, List[SalesRecord]]()) { (agg, record) =>
        val key = config.splitFunc(record)
        if (agg.contains(key)) {
          val current = agg(key)
          agg + (key -> (record :: current))
        } else {
          agg + (key -> List(record))
        }
      }

      val flattenMap = Flow[Map[String, List[SalesRecord]]].mapConcat(a => a.toList)

      val toFeatures = Flow[(String, List[SalesRecord])].map { entry =>
        val features = config.featuresFunc.map(featureFunc => featureFunc.func(entry._2)).foldLeft("")(_ + "," + _)
        s"${entry._1}$features"
      }

      val toProducerRecord = Flow[String].map { record =>
        new ProducerRecord[Array[Byte], String](config.topic, record)
      }

      bcast ~> filter ~> commands ~> aggregator ~> flatten ~> groupedBySplit ~> flattenMap ~> toFeatures ~> toProducerRecord ~> merge

    }
    FlowShape(bcast.in, merge.out)
  }
  subFlow.via(graph).map { a =>
    logger.info(s"Producer Record: $a")
    new ProducerRecord[Array[Byte], String](a.topic, a.value())
  }.to(Producer.plainSink(producerSettings)).run
  //subFlow.via(graph).to(Producer.plainSink(producerSettings)).run()

  //.to(Producer.plainSink(producerSettings)).run()

  /*configs.foreach { config =>
    val sour = Source.tick(0 seconds, 100 millisecond, new ProducerRecord[Array[Byte], String](config.topic, "Hola David"))
    sour.runWith(Producer.plainSink(producerSettings))
  }*/

}
