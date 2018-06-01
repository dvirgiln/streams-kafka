package com.david.ts.consumer

import java.time.{ Instant, OffsetDateTime, ZoneId }

/**
 * Created by dvirgil on 10/4/17.
 */
object ConsumerUtils {
  def tsToString(ts: Long) = OffsetDateTime
    .ofInstant(Instant.ofEpochMilli(ts), ZoneId.systemDefault())
    .toLocalTime
    .toString
  def toImmutable[A](elements: Iterable[A]) =
    new scala.collection.immutable.Iterable[A] {
      override def iterator: Iterator[A] = elements.toIterator
    }

}
