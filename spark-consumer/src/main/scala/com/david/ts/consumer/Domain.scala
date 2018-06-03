package com.david.ts.consumer

import scala.concurrent.duration.Duration

object Domain {
  case class ConfigConsumer(topic: String, windowDuration: Duration)

}
