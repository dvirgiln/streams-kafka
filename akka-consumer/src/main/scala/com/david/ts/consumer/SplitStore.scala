package com.david.ts.consumer

import java.io.{ File, PrintWriter }

object SplitStore {

  final val FILE_PATH = "/tmp/splits.txt"
  val ids = collection.mutable.Map[String, Int]()
  var id = 0

  def getId(split: String): Int = {
    ids.get(split).getOrElse {
      id = id + 1
      ids += (split -> id)
      id
    }
  }

  def saveToFile(): Unit = {
    val file = new File(FILE_PATH)
    if (file.exists()) {
      file.delete()
    }

    new PrintWriter(FILE_PATH) {
      ids.foreach { record =>
        println(s"${record._1}=${record._2}")
      }
      close
    }
  }
}
