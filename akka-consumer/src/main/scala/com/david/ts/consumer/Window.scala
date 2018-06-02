package com.david.ts.consumer

import scala.concurrent.duration._

case class Window(from: Long, to: Long, duration: Option[Long] = None) {
  override def toString() = s"From ${ConsumerUtils.tsToString(from)} to ${ConsumerUtils.tsToString(to)}"
}
object Window {
  def windowsFor(ts: Long, duration: FiniteDuration): Set[Window] = {
    //As the window length and window step is one, it will return just a Set of one window.
    val WindowLength = duration.toMillis
    val WindowStep = duration.toMillis
    val WindowsPerEvent = (WindowLength / WindowStep).toInt

    val firstWindowStart = ts - ts % WindowStep - WindowLength + WindowStep
    (for (i <- 0 until WindowsPerEvent) yield Window(
      firstWindowStart + i * WindowStep,
      firstWindowStart + i * WindowStep + WindowLength, Some(duration.toMillis)
    )).toSet
  }
}