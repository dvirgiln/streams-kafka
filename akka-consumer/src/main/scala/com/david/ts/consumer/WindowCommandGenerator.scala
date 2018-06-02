package com.david.ts.consumer

import com.david.ts.domain.Domain.SalesRecord

import scala.collection.mutable
import scala.concurrent.duration._
/**
 * Created by dvirgil on 10/4/17.
 */
sealed trait WindowCommand {
  def w: Window
}

case class OpenWindow(w: Window) extends WindowCommand
case class CloseWindow(w: Window) extends WindowCommand
case class AddToWindow(ev: SalesRecord, w: Window) extends WindowCommand

class WindowCommandGenerator(frequencies: Seq[FiniteDuration]) {
  private val MaxDelay = 5.seconds.toMillis
  private var watermark = 0L
  private val openWindows = mutable.Set[Window]()

  def forEvent(timestamp: Long, event: SalesRecord): List[WindowCommand] = {
    watermark = math.max(watermark, timestamp - MaxDelay)
    if (timestamp < watermark) {
      println(s"Dropping event ${event} with timestamp: ${ConsumerUtils.tsToString(timestamp)}")
      Nil
    } else {
      val closeCommands = openWindows.flatMap { ow =>
        if (ow.to < watermark) {
          openWindows.remove(ow)
          Some(CloseWindow(ow))
        } else {
          None
        }
      }

      //This is key. It checks the different frequencies and it filters out the frequencies that already have an open window.
      val toBeOpen = frequencies.filter(w => openWindows.forall(ow => ow.duration.get != w.toMillis))

      val openCommands = toBeOpen.flatMap { duration =>
        //it creates a list of windows with the frequency defined by the config.
        val listWindows = Window.windowsFor(timestamp, duration)
        listWindows.flatMap { w =>
          openWindows.add(w)
          Some(OpenWindow(w))
        }
      }

      val addCommands = openWindows.map(w => AddToWindow(event, w))
      openCommands.toList ++ closeCommands.toList ++ addCommands.toList

    }
  }
}