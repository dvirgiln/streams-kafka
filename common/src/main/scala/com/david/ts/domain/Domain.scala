package com.david.ts.domain

import scala.concurrent.duration.FiniteDuration
import scala.concurrent.duration._
object Domain {
  case class SalesRecord(transactionTimestamp: Long, shopId: Int, productId: Int, amount: Int, totalCost: Double)
  case class Config(id: Int, frequency: FiniteDuration, name: String, splitFunc: SalesRecord => String, featuresFunc: Seq[FeatureFunc], topic: String)
  case class FeatureFunc(name: String, func: Seq[SalesRecord] => Double)
}

object Configs {
  import Domain._

  val totalCostFunc = (a: Seq[SalesRecord]) => a.foldLeft(0d)((agg, record) => agg + record.totalCost)
  val countTransactions = (a: Seq[SalesRecord]) => a.size.toDouble
  val countItems = (a: Seq[SalesRecord]) => a.foldLeft(0d)((agg, record) => agg + record.amount)

  val configs = Seq(
    Config(id = 1, frequency = 5 seconds, name = "shop", splitFunc = a => s"${a.shopId}",
      featuresFunc = Seq(
        FeatureFunc("sum", totalCostFunc),
        FeatureFunc("count_transactions", countTransactions),
        FeatureFunc("count_items", countItems)
      ), topic = "shop_features"),
    Config(id = 2, frequency = 12 seconds, name = "shop_product", splitFunc = a => s"${a.shopId}_${a.productId}",
      featuresFunc = Seq(
        FeatureFunc("sum", totalCostFunc),
        FeatureFunc("count_transactions", countTransactions),
        FeatureFunc("count_items", countItems)
      ), topic = "shop_product_features")

  )
}
