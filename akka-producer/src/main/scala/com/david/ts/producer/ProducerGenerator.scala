package com.david.ts.producer
import com.david.ts.domain.Domain.SalesRecord
import com.david.ts.domain._

import scala.util.Random
object ProducerGenerator {

  val productCost: Map[Int, Double] = Map(1 -> 9.99, 2 -> 40, 3 -> 15, 4 -> 22, 5 -> 34)
  val shopsProb: Map[Int, Double] = Map(1 -> 0.025, 2 -> 0.025, 3 -> 0.3, 4 -> 0.025, 5 -> 0.1, 6 -> 0.25, 7 -> 0.35, 8 -> 0.05, 9 -> 0.25, 10 -> 0.75)

  val productsByShopProb: Map[Int, Map[Int, Double]] = Map(
    1 -> Map(1 -> 0.3, 2 -> 0.35, 3 -> 0.15, 4 -> 0.2),
    2 -> Map(1 -> 0.35, 2 -> 0.35, 3 -> 0.15, 4 -> 0.15),
    3 -> Map(1 -> 0.3, 2 -> 0.35, 3 -> 0.15, 4 -> 0.4),
    4 -> Map(1 -> 0.3, 2 -> 0.35, 3 -> 0.15, 4 -> 0.2),
    5 -> Map(1 -> 0.3, 2 -> 0.35, 3 -> 0.15, 4 -> 0.2),
    6 -> Map(1 -> 0.3, 2 -> 0.2, 3 -> 0.1, 5 -> 0.4),
    7 -> Map(1 -> 0.3, 2 -> 0.35, 3 -> 0.15, 4 -> 0.2),
    8 -> Map(1 -> 0.3, 2 -> 0.35, 3 -> 0.15, 4 -> 0.2),
    9 -> Map(1 -> 0.3, 2 -> 0.1, 3 -> 0.45, 4 -> 0.15),
    10 -> Map(1 -> 0.1, 2 -> 0.25, 3 -> 0.15, 4 -> 0.5)
  )

  val numberItemsProb: Map[Int, Double] = Map(1 -> 0.75, 2 -> 0.2, 3 -> 0.05)

  val randomDelay = new Random()

  def generateRecords(numberRecords: Int): Seq[SalesRecord] = {
    val randomPartners = new Random()

    (1 to numberRecords).map { _ =>
      val shopId: Int = randomValue(shopsProb)
      val product = randomValue(productsByShopProb(shopId))
      val numberOfItems = randomValue(numberItemsProb)
      val now = System.currentTimeMillis()
      val delay = randomDelay.nextInt(3)
      val timestamp = now - delay * 1000L

      SalesRecord(timestamp, shopId, product, numberOfItems, productCost(product) * numberOfItems)
    }
  }

  private final def randomValue[A](dist: Map[A, Double]): A = {
    val p = scala.util.Random.nextDouble
    val it = dist.iterator
    var accum = 0.0
    while (it.hasNext) {
      val (item, itemProb) = it.next
      accum += itemProb
      if (accum >= p)
        return item // return so that we don't have to search through the whole distribution
    }
    sys.error(f"this should never happen") // needed so it will compile
  }
}
